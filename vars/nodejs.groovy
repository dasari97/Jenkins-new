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

    stage('Check Code Quality') {
      steps { 
        sh """ sonar-scanner -Dsonar.projectKey=${COMPONENT} -Dsonar.sources=. -Dsonar.host.url=http://172.31.15.90:9000 -Dsonar.login=${Sonar_Token} """
      }
    }

    stage('Lint Checks') {
      steps {
        echo 'Checking Lint Checks'
      }
    }

    stage('Unit Tests') {
      steps {
        echo 'Unit tests'
      }
    }

    stage('Prepare Artifact') {
      steps {
        sh """
          cd static
          zip -r ${COMPONENT}.zip * 
        """
      }
    }

    stage('Publish Artifacts') {
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