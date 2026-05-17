param(
    [string]$Source = "H:\MinecraftMods\ExtraDelight",
    [string]$Dest = "H:\MinecraftMods\ExtraDelight-1.20.1",
    [switch]$Generated
)

if ($Generated) {
    $srcMain = "$Source\src\generated\resources"
    $dstMain = "$Dest\src\generated\resources"
} else {
    $srcMain = "$Source\src\main\resources"
    $dstMain = "$Dest\src\main\resources"
}

$copyCount = 0
$skipCount = 0

function Get-AllFiles($dir) {
    Get-ChildItem -Path $dir -Recurse -File -ErrorAction SilentlyContinue | ForEach-Object {
        $relPath = $_.FullName.Substring($dir.Length + 1)
        [PSCustomObject]@{
            RelativePath = $relPath
            FullPath = $_.FullName
            Length = $_.Length
        }
    }
}

Write-Host "Scanning source: $srcMain"
$srcFiles = @{}
Get-AllFiles $srcMain | ForEach-Object { $srcFiles[$_.RelativePath] = $_ }

Write-Host "Scanning destination: $dstMain"
$dstFiles = @{}
Get-AllFiles $dstMain | ForEach-Object { $dstFiles[$_.RelativePath] = $_ }

Write-Host "Source files: $($srcFiles.Count)"
Write-Host "Destination files: $($dstFiles.Count)"

$toCopy = @()
$toCopyReverse = @()

foreach ($key in $srcFiles.Keys) {
    if (-not $dstFiles.ContainsKey($key)) {
        $toCopy += $srcFiles[$key]
    }
}

foreach ($key in $dstFiles.Keys) {
    if (-not $srcFiles.ContainsKey($key)) {
        $toCopyReverse += $dstFiles[$key]
    }
}

Write-Host "Need to copy (source -> dest): $($toCopy.Count) files"
Write-Host "Need to copy (dest -> source): $($toCopyReverse.Count) files"

foreach ($file in $toCopy) {
    $dstPath = Join-Path $dstMain $file.RelativePath
    $dstDir = Split-Path $dstPath -Parent

    if (-not (Test-Path $dstDir)) {
        New-Item -ItemType Directory -Path $dstDir -Force | Out-Null
    }

    Copy-Item -Path $file.FullPath -Destination $dstPath -Force
    $copyCount++
    Write-Host "Copied: $($file.RelativePath)"
}

$reverseCopyCount = 0
foreach ($file in $toCopyReverse) {
    $srcPath = Join-Path $srcMain $file.RelativePath
    $srcDir = Split-Path $srcPath -Parent

    if (-not (Test-Path $srcDir)) {
        New-Item -ItemType Directory -Path $srcDir -Force | Out-Null
    }

    Copy-Item -Path $file.FullPath -Destination $srcPath -Force
    $reverseCopyCount++
    Write-Host "Reverse copied: $($file.RelativePath)"
}

Write-Host ""
Write-Host "=== Summary ==="
Write-Host "Copied: $copyCount files"
Write-Host "Reverse copied: $reverseCopyCount files"
Write-Host "Skipped: $($srcFiles.Count - $toCopy.Count) files"