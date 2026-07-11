$f1 = "c:\Users\luizs\OneDrive\Desktop\cognitio _core\cognitiocore-template-1.21\src\main\java\com\cognitio\core\perception\PerceptionEngine.java"
$c1 = Get-Content $f1 -Raw
$c1 = $c1.Replace("package com.cognitio.api.perception;", "package com.cognitio.core.perception;")
Set-Content $f1 $c1

$f2 = "c:\Users\luizs\OneDrive\Desktop\cognitio _core\cognitiocore-template-1.21\src\main\java\com\cognitio\core\attachment\AttachmentRegister.java"
$c2 = Get-Content $f2 -Raw
$c2 = $c2.Replace("package com.cognitio.attachment;", "package com.cognitio.core.attachment;")
Set-Content $f2 $c2

$f3 = "c:\Users\luizs\OneDrive\Desktop\cognitio _core\cognitiocore-template-1.21\src\main\java\com\cognitio\core\attachment\InsightData.java"
$c3 = Get-Content $f3 -Raw
$c3 = $c3.Replace("package com.cognitio.attachment;", "package com.cognitio.core.attachment;")
Set-Content $f3 $c3

$f4 = "c:\Users\luizs\OneDrive\Desktop\cognitio _core\cognitiocore-template-1.21\src\main\java\com\cognitio\core\event\InsightEventHandler.java"
$c4 = Get-Content $f4 -Raw
$c4 = $c4.Replace("package com.cognitio.event;", "package com.cognitio.core.event;")
Set-Content $f4 $c4

$f5 = "c:\Users\luizs\OneDrive\Desktop\cognitio _core\cognitiocore-template-1.21\src\main\java\com\cognitio\core\event\ModCommands.java"
$c5 = Get-Content $f5 -Raw
$c5 = $c5.Replace("package com.cognitio.event;", "package com.cognitio.core.event;")
Set-Content $f5 $c5
