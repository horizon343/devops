pipeline {
    agent any
    stages {
        stage('Display System Information') {
            steps {
                script {
                    def diskInfo = sh(script: 'df -h', returnStdout: true).trim()
                    echo "Disk Usage:\n${diskInfo}"

                    def topProcess = sh(script: 'ps -eo pid,comm,%mem --sort=-%mem | head -n 2 | tail -n 1', returnStdout: true).trim()
                    echo "Process with highest memory usage:\n${topProcess}"
                }
            }
        }
    }
}