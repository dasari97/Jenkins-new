def call (String COMPONENT) {
    pipeline {

  agent {
    node {
      label "NODEJS"
    }
  }
  environment {
      Sonar_Token = credentials('Sonar_Token')
  }
  stages {

    stage('Pushing Code for Quality check') {
      steps { 
        sh """ sonar-scanner -Dsonar.projectKey=${COMPONENT} -Dsonar.sources=. -Dsonar.host.url=http://172.31.25.74:9000 -Dsonar.login=${Sonar_Token} """
      }
    }
    
    stage('Code Quality result'){
        steps {
            sh "sonar-qulaity-gate.sh admin admin123 3.94.119.157 ${COMPONENT}"
        }
    }

    stage('Checking for Lints ') {
      steps {
        echo 'Checking Lint Checks'
      }
    }

    stage('Unit Tests') {
      steps {
        echo 'Unit tests'
      }
    }

    stage('Preparing Artifact') {
      steps {
        sh """
          cd static
          zip -r ${COMPONENT}.zip * 
        """
      }
    }

    stage('Publishing Artifacts') {
      steps {
        echo 'Publish Artifacts'
      }
    }

  }

  post {
    always {
      cleanWs()
    }
  }
}


}