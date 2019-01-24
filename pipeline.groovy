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
                sh '''
                  docker build . -f Dockerfile -t 494526681395.dkr.ecr.ap-southeast-1.amazonaws.com/dddsample
                  docker push 494526681395.dkr.ecr.ap-southeast-1.amazonaws.com/dddsample
                '''
            }
        }

        stage ('dev deploy') {
            steps {
                sh 'echo deploy to dev environment'
            }
        }
    }
}
