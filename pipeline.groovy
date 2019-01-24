def label = "docker-${UUID.randomUUID().toString()}"
podTemplate(label: label, yaml: '''
apiVersion: v1
kind: Pod
spec:
  containers:
  - name: maven
    image: maven:alpine
    command:
    - cat
    tty: true
  - name: docker
    image: docker:1.11
    command: ['cat']
    tty: true
    volumeMounts:
    - name: dockersock
      mountPath: /var/run/docker.sock
  volumes:
  - name: dockersock
    hostPath:
      path: /var/run/docker.sock
'''
) {
    def image = "jenkins/jnlp-slave"
    node(label){
//        stage ('test'){
//            container('maven') {
//                sh "mvn test"
//            }
//        }
//
//        stage ('build'){
//            container('maven') {
//                sh "mvn clean install -Dmaven.test.skip=true"
//            }
//        }

        stage ('push') {
            container('docker') {
                sh './image-push.sh'
            }
        }

        stage ('dev deploy') {
            container('docker') {
                sh 'echo WIP: deploy to dev environment'
            }
        }
    }
}
