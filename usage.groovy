pipeline {
    agent any
    stages {
        stage('Display System Information') {
            steps {
                script {
                    def diskInfo = powershell(script: 'Get-Disk | Format-Table -AutoSize | Out-String', returnStdout: true).trim()
                    echo "Disk Usage:\n${diskInfo}"

                    def topProcess = powershell(script: 'Get-Process | Sort-Object -Property WS -Descending | Select-Object -First 1 | Format-Table Name,Id,@{Label="Memory(MB)";Expression={[math]::Round($_.WS/1MB,2)}} -AutoSize | Out-String', returnStdout: true).trim()
                    echo "Process with highest memory usage:\n${topProcess}"
                }
            }
        }
    }
}