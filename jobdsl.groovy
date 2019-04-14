import templates.Template
Template.createPipelineJobWithTriggers(pipelineJob('backbase-maven-project'),
    [repoName: 'Backbase/recruitment-devops-engineer-assignment', fileName: 'Jenkinsfile',  targetBranchRegex: '*',
    buildOnMergeRequestEvents : true, buildOnPushEvents: true])
