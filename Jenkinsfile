pipeline {
   agent any
   stages {
      stage('All In One Build') {
         steps {
            timestamps {
                sh './gradlew'
            }
         }
      }
   }
}