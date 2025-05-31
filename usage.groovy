pipeline {
    agent any
    stages {
        stage('Checkout SCM') {
            steps {
                checkout scm
                echo "Successfully checked out repository"
            }
        }
        stage('Display System Information') {
            steps {
                script {
                    echo "Running on OS: ${isUnix() ? 'Linux/Unix' : 'Windows'}"
                    if (isUnix()) {
                        def diskInfo = sh(script: 'df -h', returnStdout: true).trim()
                        echo "Disk Usage:\n${diskInfo}"
                        def topProcess = sh(script: 'ps -eo pid,comm,%mem --sort=-%mem | head -n 2 | tail -n 1', returnStdout: true).trim()
                        echo "Process with highest memory usage:\n${topProcess}"
                    } else {
                        def diskInfo = powershell(script: 'Get-Disk | Format-Table -AutoSize | Out-String', returnStdout: true).trim()
                        echo "Disk Usage:\n${diskInfo}"
                        def topProcess = powershell(script: 'Get-Process | Sort-Object -Property WS -Descending | Select-Object -First 1 | Format-Table Name,Id,@{Label="Memory(MB)";Expression={[math]::Round($_.WS/1MB,2)}} -AutoSize | Out-String', returnStdout: true).trim()
                        echo "Process with highest memory usage:\n${topProcess}"
                    }
                }
            }
        }
    }
}