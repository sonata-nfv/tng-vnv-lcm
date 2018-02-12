pipeline {
   agent any
   stages {
      stage('All In One Build') {
         steps {
            timestamps {
                sh 'export'
                sh 'docker run --rm -v /usr/bin/docker:/usr/bin/docker -v /var/run/docker.sock:/var/run/docker.sock -v ${PWD}:/src -w /src java:8 ./gradlew'
            }
         }
      }
   }
}