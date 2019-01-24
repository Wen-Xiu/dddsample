pipeline{
    agent any
    tools{
        maven 'maven'
        jdk 'java_8'
    }
    stages {
        stage ("initialize") {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/Wen-Xiu/dddsample.git']]])
            }
        }
        stage ('test'){
            steps {
                sh "mvn test"
            }
        }

        stage ('build'){
            steps {
                sh "mvn clean install -Dmaven.test.skip=true"
            }
        }

        stage ('push') {
            steps {
                sh './image-push.sh'
            }
        }

        stage ('dev deploy') {
            steps {
                sh 'echo WIP: deploy to dev environment'
            }
        }
    }
}
