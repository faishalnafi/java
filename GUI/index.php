<?php

use GuzzleHttp\Client as GuzzleClient;
use Predis\Client as PredisClient;
use GuzzleHttp\Psr7\Request;

// Encrypt
if (!function_exists('encrypt')) {
    function encrypt($key, $iv, $plaintext)
    {
        $iv = substr($iv, 0, 16);
        $ciphertext = openssl_encrypt($plaintext, 'aes-256-cbc', $key, OPENSSL_RAW_DATA, $iv);
        $ivCiphertext =  $ciphertext;
        $ivCiphertextB64 = base64_encode($ivCiphertext);
        return $ivCiphertextB64;
    }
}

// Decrypt
if (!function_exists('decrypt')) {
    function decrypt($key, $iv, $ivCiphertextB64)
    {
        $ivCiphertext  = base64_decode($ivCiphertextB64);
        $iv = substr($iv, 0, 16);
        $decryptedData = openssl_decrypt($ivCiphertext, "aes-256-cbc", $key, OPENSSL_RAW_DATA, $iv);
        return $decryptedData;
    }
}

if (!function_exists('getBrowser')) {

    function getBrowser()
    {
        $u_agent = $_SERVER['HTTP_USER_AGENT'];
        $bname = 'Unknown';
        $platform = 'Unknown';
        $version = "";
        //First get the platform?
        if (preg_match('/linux/i', $u_agent)) {
            $platform = 'linux';
        } elseif (preg_match('/macintosh|mac os x/i', $u_agent)) {
            $platform = 'mac';
        } elseif (preg_match('/windows|win32/i', $u_agent)) {
            $platform = 'windows';
        }

        // Next get the name of the useragent yes seperately and for good reason
        if (preg_match('/MSIE/i', $u_agent) && !preg_match('/Opera/i', $u_agent)) {
            $bname = 'Internet Explorer';
            $ub = "MSIE";
        } elseif (preg_match('/Firefox/i', $u_agent)) {
            $bname = 'Mozilla Firefox';
            $ub = "Firefox";
        } elseif (preg_match('/OPR/i', $u_agent)) {
            $bname = 'Opera';
            $ub = "Opera";
        } elseif (preg_match('/Chrome/i', $u_agent) && !preg_match('/Edge/i', $u_agent)) {
            $bname = 'Google Chrome';
            $ub = "Chrome";
        } elseif (preg_match('/Safari/i', $u_agent) && !preg_match('/Edge/i', $u_agent)) {
            $bname = 'Apple Safari';
            $ub = "Safari";
        } elseif (preg_match('/Netscape/i', $u_agent)) {
            $bname = 'Netscape';
            $ub = "Netscape";
        } elseif (preg_match('/Edge/i', $u_agent)) {
            $bname = 'Edge';
            $ub = "Edge";
        } elseif (preg_match('/Trident/i', $u_agent)) {
            $bname = 'Internet Explorer';
            $ub = "MSIE";
        } elseif (preg_match('/CandyCBTBro/i', $u_agent)) {
            preg_match_all('/(.*)\/(.*)/m', $u_agent, $matches, PREG_SET_ORDER, 0);
            return array(
                'userAgent' => $u_agent,
                'name'      => $matches[0][1],
                'version'   => $matches[0][2],
                'platform'  => "Android",
                'pattern'    => ""
            );
        }

        // finally get the correct version number
        $known = array('Version', $ub, 'other');
        $pattern = '#(?<browser>' . join('|', $known) .
            ')[/ ]+(?<version>[0-9.|a-zA-Z.]*)#';
        if (!preg_match_all($pattern, $u_agent, $matches)) {
            // we have no matching number just continue
        }
        // see how many we have
        $i = count($matches['browser']);
        if ($i != 1) {
            //we will have two since we are not using 'other' argument yet
            //see if version is before or after the name
            if (strripos($u_agent, "Version") < strripos($u_agent, $ub)) {
                $version = $matches['version'][0];
            } else {
                $version = $matches['version'][1];
            }
        } else {
            $version = $matches['version'][0];
        }

        // check if we have a number
        if ($version == null || $version == "") {
            $version = "?";
        }

        return array(
            'userAgent' => $u_agent,
            'name'      => $bname,
            'version'   => $version,
            'platform'  => $platform,
            'pattern'    => $pattern
        );
    }
}

if (!function_exists('save_options')) {
    /**
     * @param string|array $data array or string value
     * @param string|int $key name or id 
     */
    function save_options($data, $key = null)
    {
        global $options;
        try {
            //code...
            $db = db_connect();
            $db->transStart();
            // $byId = [];
            $byName = [];
            if (redison()) {
                $redisConfig = json_decode(options('redis-config'), true);
                if ($redisConfig['state']) {
                    try {
                        $redisClient = new PredisClient([
                            'scheme' => 'tcp',
                            'host'   => $redisConfig['host'],
                            'port'   => $redisConfig['port'],
                            'password' => $redisConfig['password'],
                            'database' => $redisConfig['database'],
                        ]);
                        $redisClient->connect();
                    } catch (\Throwable $th) {
                        if (ENVIRONMENT != 'production') throw new Exception($th->getMessage());
                        else return false;
                    }
                }
            }
            if (is_array($data)) {
                foreach ($data as $d) {
                    foreach ($options as $k => $v) {
                        // if (isset($d['id'])) {
                        //     $byId[] = $d;
                        // } else {
                        if ($v['option_name'] != $d['option_name']) continue;
                        if (redison()) {
                            if (is_json($d['option_value'])) $options[$k]['option_value'] = json_decode($d['option_value']);
                            else $options[$k]['option_value'] = $d['option_value'];
                        }
                        $byName[] = $d;
                        // }
                    }
                }

                // if ($byId) $db->table('options')->updateBatch($byId, 'id');
                if ($byName) $db->table('options')->updateBatch($byName, 'option_name');
            } else {
                $db->table('options')->orWhere('option_name', $key)->update(['option_value' => $data]);
                if (redison()) {
                    foreach ($options as $k => $v) {
                        if ($v['option_name'] != $key) continue;
                        if (is_json($data)) $options[$k]['option_value'] = json_decode($data);
                        else $options[$k]['option_value'] = $data;
                    }
                }
            }
        } catch (\Throwable $th) {
            if (ENVIRONMENT != 'production') throw new Exception($th->getMessage());
            return false;
        }
        if (redison()) $redisClient->set("app-options", json_encode($options));
        $db->transComplete();
        return !($db->transStatus() === FALSE);
    }
}
if (!function_exists('is_https')) {
    function is_https()
    {
        return (strpos(current_url(), 'https') !== false);
    }
}
if (!function_exists('secondsToTime')) {

    function secondsToTime($inputSeconds, $normal = false)
    {
        $secondsInAMinute = 60;
        $secondsInAnHour = 60 * $secondsInAMinute;
        $secondsInADay = 24 * $secondsInAnHour;

        // extract days
        $days = floor($inputSeconds / $secondsInADay);

        // extract hours
        $hourSeconds = $inputSeconds % $secondsInADay;
        $hours = floor($hourSeconds / $secondsInAnHour);

        // extract minutes
        $minuteSeconds = $hourSeconds % $secondsInAnHour;
        $minutes = floor($minuteSeconds / $secondsInAMinute);

        // extract the remaining seconds
        $remainingSeconds = $minuteSeconds % $secondsInAMinute;
        $seconds = ceil($remainingSeconds);

        // return the final array
        $obj = [
            "d" => $days,
            "h" => $hours,
            "m" => $minutes,
            "s" => ($normal ? $seconds : ($seconds >= 1 ? $seconds - 1 : $seconds))
        ];
        return $obj;
    }
}
if (!function_exists('requirement_invalid')) {
    function requirement_invalid()
    {
        $text = [];
        if (!extension_loaded('intl')) $text[] = "Extension intl tidak aktif, silahkan aktifkan di php.ini";
        if (!class_exists('ZipArchive')) $text[] = "Class ZipArchive tidak ditemukan, silahkan install zip di php";
        return $text;
    }
}
if (!function_exists('options')) {
    function options($name, $errReport = false)
    {
        try {
            if (is_string($name)) {
                //delete or define your secret for mobile login
                if ($name == "MOBILEPASSKEY") {
                    return "SECRETKEYADALAHRAHASIA";
                }
                $opt = $_ENV[$name] ?? getenv($name);
                if ($opt) return $opt;
            }
        } catch (\Throwable $th) {
        }
        global $options;
        if (!isset($GLOBALS['redis-config'])) {
            try {
                $rc = db_connect()->table('options')->getWhere(['option_name' => 'redis-config'])->getRowArray();
                $GLOBALS['redis-config'] = json_decode($rc['option_value'], true);
                if ($GLOBALS['redis-config']['state']) {
                    $redisClient = new PredisClient([
                        'scheme' => 'tcp',
                        'host'   => $GLOBALS['redis-config']['host'],
                        'port'   => $GLOBALS['redis-config']['port'],
                        'password' => $GLOBALS['redis-config']['password'],
                        'database' => $GLOBALS['redis-config']['database'],
                    ]);
                    $redisClient->connect();
                    $cfg = $redisClient->get("app-options");
                    if ($cfg) $options = json_decode($cfg, true);
                    else {
                        $options = db_connect()->table('options')->get()->getResultArray();
                        $redisClient->set("app-options", json_encode($options));
                    }
                }
            } catch (\Throwable $th) {
                //throw $th;
            }
        }
        if (is_string($name) && get_option($name)) return get_option($name);
        try {
            $opt = db_connect()->table('options');
            $opt->whereIn('option_name', is_string($name) ? [$name] : $name);
            $opt = $opt->get()->getResultArray();
            $o = [];
            foreach ($opt as $op) {
                if (is_array($name)) $o[] = $op;
                if (!get_option($op['option_name'])) $options[] = $op;
            }
            if (is_array($name)) return $o;
        } catch (\Throwable $th) {
            if (!$errReport) {
                return null;
            } else {
                throw new Exception($th->getMessage());
            }
        }
        return get_option($name);
    }
}
if (!function_exists('sendOneSignal')) {

    function sendOneSignal($data)
    {
        $client = new GuzzleClient();
        try {
            $oc = json_decode(options('onesignal_config'), true);
            $data['app_id'] = $oc['app_id'];
            if (!empty($oc['key']) && !empty($oc['app_id'])) {
                $response = $client->request('POST', 'https://onesignal.com/api/v1/notifications', [
                    'json' => $data,
                    'headers' => [
                        'Authorization' => "Basic $oc[key]"
                    ],
                ]);
                if ($response->getStatusCode() == 200) return json_decode($response->getBody()->getContents(), true);
            }
        } catch (\Throwable $th) {
            if (ENVIRONMENT != 'production') throw new Exception($th->getMessage());
        }
        return false;
    }
}

if (!function_exists('get_option')) {
    function get_option($name)
    {
        global $options;
        $redisConfig = null;
        foreach (($options ?? []) as $k => $v) {
            if ($v['option_name'] == $name) return $v['option_value'];
            if ($v['option_name'] == 'redis-config') $redisConfig = json_decode($v['option_value'], true);
        }
        return null;
        // if (!$redisConfig) return null;
        // if (!isset($GLOBALS['redis-config'])) return null;
        // if (!$GLOBALS['redis-config']['state']) return null;
        // try {
        //     $redisClient = new PredisClient([
        //         'scheme' => 'tcp',
        //         'host'   => $redisConfig['host'],
        //         'port'   => $redisConfig['port'],
        //         'password' => $redisConfig['password'],
        //         'database' => $redisConfig['database'],
        //     ]);
        //     $redisClient->connect();
        //     return $redisClient->get("app-options-" . $name);
        // } catch (\Throwable $th) {
        //     return null;
        // }
    }
}
if (!function_exists('redison')) {
    function redison()
    {
        if (isset($GLOBALS['redis-config'])) return $GLOBALS['redis-config']['state'];
        try {
            $redis  = json_decode(options('redis-config'), true);
            return $redis['state'];
        } catch (\Throwable $th) {
            return false;
        }
    }
}
if (!function_exists('autoload_options')) {
    function autoload_options()
    {
        try {
            $GLOBALS['options'] = db_connect()->table('options')->get()->getResultArray();
        } catch (\Throwable $th) {
            return $th->getMessage();
        }
    }
}

if (!function_exists('autoload_option_to_redis')) {
    function autoload_option_to_redis()
    {
        try {
            $options = db_connect()->table('options')->get()->getResultArray();
            $redis = null;
            foreach ($options as $k => $v) {
                if ($v['option_name'] == 'redis-config') {
                    $redis = json_decode($v['option_value'], true);
                    break;
                }
            }
            if (!$redis) return false;
            if (!$redis['state']) return false;
            try {
                $redisClient = new PredisClient([
                    'scheme' => 'tcp',
                    'host'   => $redis['host'],
                    'port'   => $redis['port'],
                    'password' => $redis['password'],
                    'database' => $redis['database'],
                ]);
                $redisClient->connect();
                $redisClient->set('app-options', json_encode($options), 'EX', 60 * 10);
                return true;
            } catch (\Throwable $th) {
                if (ENVIRONMENT != 'production') throw new Exception($th->getMessage());
                else return false;
            }
        } catch (\Throwable $th) {
            // if (ENVIRONMENT != 'production') throw new Exception($th->getMessage());
            // else 
            return false;
        }
    }
}

if (!function_exists('setLog')) {

    function setLog($detail, $opt = [])
    {
        $request = \Config\Services::request();
        $data['detail'] = $detail;
        if (isset($opt['user_id'])) {
            $data['user_id'] = $opt['user_id'];
        } else {
            $data['user_id'] = user()->id ?? null;
        }
        if (isset($opt['fullname'])) {
            $data['fullname'] = $opt['fullname'];
        } else {
            $data['fullname'] = user()->fullname ?? (user()->nama ?? null);
        }
        if (isset($opt['user_type'])) {
            $data['user_type'] = $opt['user_type'];
        } else {
            $data['user_type'] = isset(user()->username) ? 1 : 2;
        }
        if (isset($opt['ip'])) {
            $data['ip'] = $opt['ip'];
        } else {
            $data['ip'] = $request->getIPAddress();
        }
        $data['created_at'] = time();
        if (!isset($data['user_id'])) return false;
        return db_connect()->table('log_activity')->insert($data);
    }
}

if (!function_exists('get_time')) {
    function get_time($day, $time = "00:00:00")
    {
        $add = 0;
        $d = date("w", strtotime("+$add days $time"));
        while ($d + 1 != $day) {
            $add++;
            $d = date("w", strtotime("+$add days $time"));
        }
        return strtotime("+$add days $time");
    }
}
if (!function_exists('bulanIndo')) {
    function bulanIndo($index)
    {
        $index = (int)$index;
        $bulan = [
            1 => 'Januari',
            'Februari',
            'Maret',
            'April',
            'Mei',
            'Juni',
            'Juli',
            'Agustus',
            'September',
            'Oktober',
            'November',
            'Desember'
        ];
        return $bulan[$index];
    }
}
if (!function_exists('compress_and_resize_image')) {

    /**
     * Compress and resize image
     *
     * @param string $src Image Source Path
     * 
     * @param string $dst Image Destination Path
     *
     * @param string $quality = 70 Image Quality
     * 
     * @param string|array $sizes Image Size
     * example in pixel ['width'=>200,'height'=>200].
     * example in percent 0.5 
     *
     * @return $this
     */
    function compress_and_resize_image($src, $dst, $quality = 70, $sizes = ['width' => 200, 'height' => 200])
    {
        $c =  compress_image($src, $dst, $quality);
        if (!$c) return false;
        return resize_image($dst, $dst, $sizes);
    }
}
if (!function_exists('compress_image')) {
    function compress_image($src, $dst, $quality = 70)
    {
        $info = getimagesize($src);
        if ($info['mime'] == 'image/jpeg' || $info['mime'] == 'image/jpg') $image = imagecreatefromjpeg($src);
        elseif ($info['mime'] == 'image/gif') $image = imagecreatefromgif($src);
        elseif ($info['mime'] == 'image/png') $image = imagecreatefrompng($src);
        if ($info['mime'] == 'image/jpeg' || $info['mime'] == 'image/jpg') imagejpeg($image, $dst, $quality);
        elseif ($info['mime'] == 'image/gif') imagegif($image, $dst);
        elseif ($info['mime'] == 'image/png') imagepng($image, $dst, floor($quality > 0 ? ($quality > 90 ? 9 : $quality / 10) : $quality));
    }
}
if (!function_exists('versioningjs')) {
    function versioningjs()
    {
        if (ENVIRONMENT != 'production') {
            return uniqid('dev');
        } else {
            return md5(APP_VERSION_CODE);
        }
    }
}
if (!function_exists('hcsrf')) {
    function hcsrf()
    {
        $csrf = json_encode(['name' => csrf_token(), 'value' => csrf_hash()]);
        return ":hcsrf='$csrf'";
    }
}
if (!function_exists('resize_image')) {
    function resize_image($src, $dst, $sizes)
    {
        // Get new dimensions
        list($width, $height) = getimagesize($src);
        if (is_array($sizes)) {
            $new_width = $sizes['width'];
            $new_height = $sizes['height'];
        } else {
            $new_width = $width * $sizes;
            $new_height = $height * $sizes;
        }

        // Resample
        $image_p = imagecreatetruecolor($new_width, $new_height);
        imagealphablending($image_p, false);
        imagesavealpha($image_p, true);
        $transparent = imagecolorallocatealpha($image_p, 255, 255, 255, 127);
        imagefilledrectangle($image_p, 0, 0, $new_width, $new_height, $transparent);
        $info = getimagesize($src);
        $src_w = imagesx($image_p);
        $src_h = imagesy($image_p);
        if ($info['mime'] == 'image/jpeg' || $info['mime'] == 'image/jpg') $image = imagecreatefromjpeg($src);
        elseif ($info['mime'] == 'image/gif') $image = imagecreatefromgif($src);
        elseif ($info['mime'] == 'image/png') $image = imagecreatefrompng($src);
        imagecopyresampled($image_p, $image, 0, 0, 0, 0, $new_width, $new_height, $width, $height);

        // Output
        if ($info['mime'] == 'image/jpeg' || $info['mime'] == 'image/jpg') return imagejpeg($image_p, $dst, 100);
        elseif ($info['mime'] == 'image/gif') return imagegif($image_p, $dst);
        elseif ($info['mime'] == 'image/png') return imagepng($image_p, $dst, 9);
    }
}
if (!function_exists('base64ToImage')) {

    function base64ToImage($str, $dst = false, $compress = true)
    {
        $replacer = [];
        $imgPath = config('app')->imgPath;
        // $re = '/src="(data:image\/(?<extension>[^;]+);base64,(?<image>[^"]+))"/';
        $re = '/(?<formula>class="fm-editor-equation" )?src="(data:image\/(?<extension>[^;]+);base64,(?<image>[^"]+))"/';
        if (preg_match_all($re, $str, $matches, PREG_SET_ORDER, 0)) {
            foreach ($matches as $k => $v) {
                if (strlen($v['formula']) > 0) continue;
                $name = uniqid() . ".$v[extension]";
                try {
                    if ($dst) $dst = trim($dst, '/');
                    $img = base64_decode($v['image']);
                    $path = $dst ? "$dst/$name" : FCPATH . "$imgPath/soal/$name";
                    if (file_put_contents($path, $img)) {
                        if ($compress) {
                            compress_image($path, $path);
                        }
                        $path = str_replace(FCPATH, base_url() . "/", $path);
                        $str = str_replace($v[2], $path, $str);
                        $replacer[] = [
                            $v[2],
                            $path
                        ];
                    }
                } catch (\Throwable $th) {
                }
            }
        }
        return ['str' => $str, 'replacer' => $replacer];
    }
}
if (!function_exists('hariIndo')) {
    function hariIndo($index)
    {
        switch ($index) {
            case 'Sun':
                return "Minggu";

            case 'Mon':
                return "Senin";

            case 'Tue':
                return "Selasa";

            case 'Wed':
                return "Rabu";

            case 'Thu':
                return "Kamis";

            case 'Fri':
                return "Jumat";

            case 'Sat':
                return "Sabtu";

            default:
                return "Tidak di ketahui";
        }
    }
}
if (!function_exists('favicon')) {
    function favicon()
    {
        try {
            $favicon = options('logo');
            if ($favicon) {
                $favicon = base_url(config('app')->imgPath . "/" . $favicon);
            } else {
                $favicon = base_url(config('app')->imgPath . '/favicon.ico');
            }
        } catch (\Throwable $th) {
            $favicon = base_url(config('app')->imgPath . '/favicon.ico');
        }
        return $favicon;
    }
}
if (!function_exists('logo')) {
    function logo()
    {
        try {
            $logo = options('logo');
            if ($logo) {
                $logo = base_url(config('app')->imgPath . "/" . $logo);
            } else {
                $logo = base_url(config('app')->imgPath . '/logo.png');
            }
        } catch (\Throwable $th) {
            $logo = base_url(config('app')->imgPath . '/logo.png');
        }
        return $logo;
    }
}
if (!function_exists('kementrian')) {
    function kementrian()
    {
        try {
            $logo = options('kementrian_image');
            if ($logo) {
                $logo = base_url(config('app')->imgPath . "/" . $logo);
            } else {
                $logo = base_url(config('app')->imgPath . '/kementrian.png');
            }
        } catch (\Throwable $th) {
            $logo = base_url(config('app')->imgPath . '/kementrian.png');
        }
        return $logo;
    }
}
if (!function_exists('ttd')) {
    function ttd()
    {
        try {
            $logo = options('ttd_image');
            if ($logo) {
                $logo = base_url(config('app')->imgPath . "/" . $logo);
            } else {
                $logo = base_url(config('app')->imgPath . '/ttd.png');
            }
        } catch (\Throwable $th) {
            $logo = base_url(config('app')->imgPath . '/ttd.png');
        }
        return $logo;
    }
}

if (!function_exists('generateRandomString')) {
    function generateRandomString($length = 6)
    {
        $characters = '123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ';
        $charactersLength = strlen($characters);
        $randomString = '';
        for ($i = 0; $i < $length; $i++) {
            $randomString .= $characters[rand(0, $charactersLength - 1)];
        }
        return $randomString;
    }
}

if (!function_exists('checkLicense')) {
    function checkLicense($code, $opt = [])
    {
        $app = config('app');
        $client = new GuzzleClient();
        try {
            foreach ($opt as $k => $v) {
                $data[$k] = $v;
            }
            $data['nyoy'] = $code;

            $res = array(
                "state" => 1,
                "expired_at" => '32505947762',
                "NPSN" => '1987654320',
                "NamaSekolah" => 'SMK DIGITAL'
                );
            $response = json_encode($res);
            if (true) return json_decode($response, true);
            else return ['state' => 2, 'msg' => ENVIRONMENT != 'production' ? $response->getBody()->getContents() : 'Ada masalah dalam menghubungi server', 'data' => []];
        } catch (\Throwable $th) {
            return ['state' => 2, 'msg' => ENVIRONMENT != 'production' ? $th->getMessage() : 'Ada masalah dalam menghubungi server', 'data' => []];
        }
    }
}
if (!function_exists('sendWA')) {
    function sendWAMessage($device_id, $opt = [])
    {
        $client = new GuzzleClient();
        $options = [
            'multipart' => [[
                'name' => 'device_id',
                'contents' => $device_id
            ]]
        ];
        foreach ($opt as $k => $o) {
            if (in_array($k, ['method', 'endpoint'])) continue;
            $options['multipart'][] = [
                'name' => $k,
                'contents' => $o
            ];
        }
        $request = new Request('POST', 'https://wa.cbtcandy.com/api/' . ($opt['endpoint'] ?? 'send'));
        try {
            $response = $client->sendAsync($request, $options)->wait();
            if ($response->getStatusCode() == 200) return json_decode($response->getBody()->getContents(), true);
            else return ['status' => false, 'message' => ENVIRONMENT != 'production' ? $response->getBody()->getContents() : 'Ada masalah dalam menghubungi server', 'data' => []];
        } catch (\Throwable $th) {
            return ['status' => false, 'message' => ENVIRONMENT != 'production' ? $th->getMessage() : 'Ada masalah dalam menghubungi server', 'data' => []];
        }
    }
}
if (!function_exists('getWADeviceInfo')) {
    function getWADeviceInfo($device_id)
    {
        $client = new GuzzleClient();
        $request = new Request('GET', "https://wa.cbtcandy.com/api/statusDevice?device_id=$device_id");
        try {
            $response = $client->sendAsync($request)->wait();
            if ($response->getStatusCode() == 200) return json_decode($response->getBody()->getContents(), true);
            else return ['status' => false, 'message' => ENVIRONMENT != 'production' ? $response->getBody()->getContents() : 'Ada masalah dalam menghubungi server', 'data' => []];
        } catch (\Throwable $th) {
            return ['status' => false, 'message' => ENVIRONMENT != 'production' ? $th->getMessage() : 'Ada masalah dalam menghubungi server', 'data' => []];
        }
    }
}
if (!function_exists('carikata')) {
    function carikata($source, $find)
    {
        if (version_compare(PHP_VERSION, '8', '>=')) return str_contains($source, $find);
        return strpos($source, $find);
    }
}
if (!function_exists('checkUpdate')) {
    function checkUpdate($code, $opt = [])
    {
        $app = config('app');
        $client = new GuzzleClient();
        try {
            foreach ($opt as $k => $v) {
                $data[$k] = $v;
            }
            $data['nyoy'] = $code;
            $response = $client->request('POST', $app->ru, [
                'form_params' => $data, 'verify' => false
            ]);
            if ($response->getStatusCode() == 200) return json_decode($response->getBody()->getContents(), true);
            else return ['state' => 2, 'msg' => ENVIRONMENT != 'production' ? $response->getBody()->getContents() : 'Ada masalah dalam menghubungi server', 'data' => []];
        } catch (\Throwable $th) {
            return ['state' => 2, 'msg' => ENVIRONMENT != 'production' ? $th->getMessage() : 'Ada masalah dalam menghubungi server', 'data' => []];
        }
    }
}
if (!function_exists('cleanString')) {

    function cleanString($string)
    {
        $string = str_replace(' ', '', $string); // Replaces all spaces with hyphens.

        $string = str_replace('-', '', $string); // Replaces all dash with hyphens.

        return preg_replace('/[^A-Za-z0-9\-]/', '', $string); // Removes special chars.
    }
}
if (!function_exists('parseAttendanceTimeToNow')) {
    function parseAttendanceTimeToNow($time)
    {
        try {
            $time = is_string($time) ? (int) $time : $time;
        } catch (\Throwable $th) {
        }
        return strtotime(date(date("Y-m-d") . " H:i:s", $time));
    }
}
if (!function_exists('is_json')) {
    function is_json($string)
    {
        try {
            json_decode($string);
        } catch (\Throwable $th) {
            return false;
        }
        return json_last_error() === JSON_ERROR_NONE;
    }
}
if (!function_exists('htmlIMAGE')) {
    function htmlIMAGE($src, $title = "Image")
    {
        return "<img src='$src' alt='$title'  />";
    }
}
if (!function_exists('htmlMP3')) {
    function htmlMP3($src, $mime = "audio/mpeg")
    {
        $h = "<audio controls controlsList='nodownload'>";
        $h .= "<source src='{$src}' type='$mime'>";
        $h .= "Your browser does not support the audio element.";
        $h .= "</audio>";
        return $h;
    }
}
if (!function_exists('htmlVideo')) {
    function htmlVideo($src, $mime)
    {
        $h = "<video controls='controls'><source src='$src' type='$mime' /></video>";
        return $h;
    }
}

if (!function_exists('noCache')) {
    function noCache($response)
    {
        $response->setHeader("Pragma", "no-cache");
        $response->setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        $response->setHeader("Cache-Control", "post-check=0, pre-check=0");
    }
}

if (!function_exists('xml_attribute')) {

    function xml_attribute($object, $attribute)
    {
        if (isset($object[$attribute]))
            return (string) $object[$attribute];
    }
}
if (!function_exists('change_version')) {
    /**
     * Ganti versi aplikasi
     * @param int $code
     * INTEGER VERSION CODE  
     * @param string $name 
     * STRING VERSION NAME
     */
    function change_version($code, $name)
    {
        $cfp = APPPATH . "Config/Constants.php";
        $cf = '';
        try {
            $handle = fopen($cfp, "r");
            while (($line = fgets($handle)) !== false) {
                if (strpos($line, 'APP_VERSION_CODE')) {
                    $line = "defined('APP_VERSION_CODE') || define('APP_VERSION_CODE', $code);\n";
                }
                if (strpos($line, 'APP_VERSION_NAME')) {
                    $line = "defined('APP_VERSION_NAME') || define('APP_VERSION_NAME', '$name');\n";
                }
                $cf .= $line;
            }
            fclose($handle);
            file_put_contents($cfp, $cf);
        } catch (\Throwable $th) {
            if (ENVIRONMENT != 'production') {
                throw new Exception($th->getMessage(), $th->getCode());
            } else {
                return false;
            }
        }
        return true;
    }
}
/**
 * @param string|array $extension
 * extension file for delete
 * @param string $dir
 * directory for delete
 * @param array $exclude
 * list of file to exclude
 * @return bool
 */
function rmbfe($extension = '*', $dir = null, $exclude = [])
{
    if (!is_dir($dir)) return false;
    $files = glob("$dir/*");
    foreach ($files as $file) {
        if (in_array(basename($file), $exclude)) continue;
        $fne = explode('.', $file);
        if (is_dir($file)) {
            rmbfe($extension, $file, $exclude);
        } elseif (is_file($file)) {
            if (is_array($extension)) {
                if (in_array(end($fne), $extension)) {
                    unlink($file);
                }
            } else if ($extension == '*') {
                unlink($file);
            } elseif (is_string($extension)) {
                if (end($fne) == $extension) {
                    unlink($file);
                }
            }
        }
    }
    return true;
}
// E
/**
 * @param string $passphrase
 * @param array $value
 * @return string
 */
if (!function_exists('rmltoado')) {
    function rmltoado($passphrase, $value)
    {
        $salt = openssl_random_pseudo_bytes(8);
        $salted = '';
        $dx = '';
        while (strlen($salted) < 48) {
            $dx = md5($dx . $passphrase . $salt, true);
            $salted .= $dx;
        }
        $key = substr($salted, 0, 32);
        $iv  = substr($salted, 32, 16);
        $encrypted_data = openssl_encrypt(json_encode($value), 'aes-256-cbc', $key, true, $iv);
        $data = array("ct" => base64_encode($encrypted_data), "iv" => bin2hex($iv), "s" => bin2hex($salt));
        return json_encode($data);
    }
}
// D
/**
 * @param string $passphrase
 * @param string $jsonString
 * @return array
 */
if (!function_exists('frltoado')) {
    function frltoado($passphrase, $jsonString)
    {
        $jsondata = json_decode($jsonString, true);
        $salt = hex2bin($jsondata["s"]);
        $ct = base64_decode($jsondata["ct"]);
        $iv  = hex2bin($jsondata["iv"]);
        $concatedPassphrase = $passphrase . $salt;
        $md5 = array();
        $md5[0] = md5($concatedPassphrase, true);
        $result = $md5[0];
        for ($i = 1; $i < 3; $i++) {
            $md5[$i] = md5($md5[$i - 1] . $concatedPassphrase, true);
            $result .= $md5[$i];
        }
        $key = substr($result, 0, 32);
        $data = openssl_decrypt($ct, 'aes-256-cbc', $key, true, $iv);
        return json_decode($data, true);
    }
}
