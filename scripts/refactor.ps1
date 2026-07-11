$files = Get-ChildItem -Path "c:\Users\luizs\OneDrive\Desktop\cognitio _core\cognitiocore-template-1.21\src\main\java" -Filter "*.java" -Recurse

foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $original = $content
    
    $content = $content.Replace("com.cognitio.core.perception.PerceptionEngine", "com.cognitio.core.perception.PerceptionTierManager")
    $content = $content.Replace("com.cognitio.api.perception.PerceptionEngine", "com.cognitio.core.perception.PerceptionEngine")
    $content = $content.Replace("com.cognitio.attachment", "com.cognitio.core.attachment")
    $content = $content.Replace("com.cognitio.event", "com.cognitio.core.event")

    if ($content -cne $original) {
        Set-Content -Path $file.FullName -Value $content
        Write-Host "Updated $($file.Name)"
    }
}

$tierManager = "c:\Users\luizs\OneDrive\Desktop\cognitio _core\cognitiocore-template-1.21\src\main\java\com\cognitio\core\perception\PerceptionTierManager.java"
$content = Get-Content $tierManager -Raw
$content = $content.Replace("public class PerceptionEngine", "public class PerceptionTierManager")
Set-Content -Path $tierManager -Value $content
