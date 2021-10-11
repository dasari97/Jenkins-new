def call (String Agent , String COMPONENT) {
    pipeline {

  agent {
    node {
      label "${Agent}"
    }
  }

  stages {

    stage('Compiling code') {
      when {
        anyOf {
          expression { COMPONENT == "JAVA" }
        }
      }
      steps {
        echo 'Code compilation done'
      }
    }

    stage('Check Code Quality') {
      steps {
        echo 'Checking Code Quality'
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