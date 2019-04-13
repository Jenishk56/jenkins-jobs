import templates.Template
Template.createPipelineJobWithTriggers(pipelineJob('backbase-maven-project'),
    [repoName: 'Backbase/recruitment-devops-engineer-assignment', fileName: 'Jenkinsfile.patchset',  targetBranchRegex: '*',
    buildOnMergeRequestEvents : true, buildOnPushEvents: true])
