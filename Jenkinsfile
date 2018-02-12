pipeline {
   agent any
   stages {
      stage('All In One Build') {
         steps {
            timestamps {
                sh 'export'
                sh 'git ls-files --stage'
                sh 'ls -alth'
                sh './gradlew'
            }
         }
      }
   }
   post {
        always {
            cleanWs()
        }
    }
}