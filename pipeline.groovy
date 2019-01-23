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
        stage ('mvn test'){
            //mvn 测试
            steps {
                sh "mvn test"
            }
        }

        stage ('mvn build'){
            //mvn构建
            steps {
                sh "mvn clean install -Dmaven.test.skip=true"
            }
        }

        stage ('publish') {
            steps {
                sh '''
                  pip install awscli
                  echo "Not logged into ECR yet, logging in"
                  login_command=$(aws ecr get-login --registry-ids 494526681395 --region eu-central-1 --no-include-email)
                  eval "${login_command}" &> /dev/null
                  if docker pull 494526681395.dkr.ecr.ap-southeast-1.amazonaws.com/dddsample; then
                  echo "Login succeeded"
                  else
                  echo "login failed"
                  fi
                  
                  docker build . -f Dockerfile -t 494526681395.dkr.ecr.ap-southeast-1.amazonaws.com/dddsample
                  docker push 494526681395.dkr.ecr.ap-southeast-1.amazonaws.com/dddsample
                '''
            }
        }
    }
}
