Add-Type -AssemblyName System.Drawing

$bmp = New-Object System.Drawing.Bitmap(16,16)
$pixels = @(
"0000000000000000",
"0000000000000000",
"0000000000000000",
"0000000000000000",
"0000011111100000",
"0001100000011000",
"0110000000000110",
"1000000110000001",
"1000000110000001",
"0110000000000110",
"0001100000011000",
"0000011111100000",
"0000000000000000",
"0000000000000000",
"0000000000000000",
"0000000000000000"
)

for ($y=0; $y -lt 16; $y++) {
    for ($x=0; $x -lt 16; $x++) {
        if ($pixels[$y][$x] -eq '1') {
            $bmp.SetPixel($x, $y, [System.Drawing.Color]::White)
        } else {
            $bmp.SetPixel($x, $y, [System.Drawing.Color]::Transparent)
        }
    }
}

$srcPath = "c:\Users\luizs\OneDrive\Desktop\cognitio _core\cognitiocore-template-1.21\src\main\resources\assets\cognitio_core\textures\gui\eye.png"
$buildPath = "c:\Users\luizs\OneDrive\Desktop\cognitio _core\cognitiocore-template-1.21\build\resources\main\assets\cognitio_core\textures\gui\eye.png"

$bmp.Save($srcPath, [System.Drawing.Imaging.ImageFormat]::Png)
if (Test-Path "c:\Users\luizs\OneDrive\Desktop\cognitio _core\cognitiocore-template-1.21\build\resources\main\assets\cognitio_core\textures\gui") {
    $bmp.Save($buildPath, [System.Drawing.Imaging.ImageFormat]::Png)
}
