param(
  [Parameter(ValueFromRemainingArguments = $true)]
  [string[]] $MavenArgs = @('spring-boot:run')
)

$ErrorActionPreference = 'Stop'
$mavenVersion = '3.9.9'
$toolsDir = Join-Path $PSScriptRoot '.tools'
$mavenHome = Join-Path $toolsDir "apache-maven-$mavenVersion"
$mavenCommand = Get-Command mvn -ErrorAction SilentlyContinue

if ($mavenCommand) {
  & $mavenCommand.Source @MavenArgs
  exit $LASTEXITCODE
}

if (-not (Test-Path (Join-Path $mavenHome 'bin\mvn.cmd'))) {
  $null = New-Item -ItemType Directory -Force -Path $toolsDir
  $archive = Join-Path $toolsDir "apache-maven-$mavenVersion-bin.zip"
  $url = "https://archive.apache.org/dist/maven/maven-3/$mavenVersion/binaries/apache-maven-$mavenVersion-bin.zip"
  Write-Host "Maven was not found on PATH. Downloading Maven $mavenVersion locally..."
  try {
    Invoke-WebRequest -Uri $url -OutFile $archive -TimeoutSec 90
    Expand-Archive -LiteralPath $archive -DestinationPath $toolsDir -Force
  } catch {
    Remove-Item -LiteralPath $archive -Force -ErrorAction SilentlyContinue
    Write-Error "Could not download Maven. Check your internet connection, or install Maven and add its bin folder to PATH. Download URL: $url. Details: $($_.Exception.Message)"
    exit 1
  }
  Remove-Item -LiteralPath $archive -Force
}

& (Join-Path $mavenHome 'bin\mvn.cmd') @MavenArgs
exit $LASTEXITCODE
