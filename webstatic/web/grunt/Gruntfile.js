module.exports = function(grunt){
		grunt.initConfig({
			pkg:  grunt.file.readJSON('package.json'),
			uglify: {
            	releaseCommon: {
                	files: {
                    	'build/libs/common/common.min.js': ['src/libs/common/*.js']
                	}
            	},
            	releaseSprites: {
                	files: {
                    	'build/libs/sprites/sprites.min.js': ['src/libs/sprites/*.js'],
                	}
            	},
            	releaseStates: {
                	files: {
                    	'build/libs/states/states.min.js': [ 'src/libs/states/*.js']
                	}
            	},
            	releaseRecord: {
                	files: {
                    	'build/libs/record/record.min.js': [ 'src/libs/record/*.js']
                	}
            	},
            	releasePokerCommon: {
                	files: {
                    	'build/libs/poker/pokercommon.min.js': [ 'src/libs/poker/common/*.js']
                	}
            	},
            	releasePokerStates: {
                	files: {
                    	'build/libs/poker/pokerstates.min.js': [ 'src/libs/poker/states/*.js']
                	}
            	},
            	releasePokerSprites: {
                	files: {
                    	'build/libs/poker/pokersprites.min.js': [ 'src/libs/poker/sprites/*.js']
                	}
            	},
            	buildjs: {
	                files: [{
	                    expand:true,
	                    cwd:'src/js',
	                    src:'*.js',
	                    dest: 'build/js/'
	                }]
	            },
	            buildmain: {
	                files: [{
	                    expand:true,
	                    cwd:'src/libs/main',
	                    src:'*.js',
	                    dest: 'build/libs/main'
	                }]
	            },
	            buildpokermain: {
	                files: [{
	                    expand:true,
	                    cwd:'src/libs/poker/main',
	                    src:'*.js',
	                    dest: 'build/libs/poker'
	                }]
	            }
			},
			copy: {
			  main: {
			    files: [
			      {expand: true,cwd:'src/',src: ['resources/**'], dest: 'build/',filter: 'isFile'},
			      {expand: true,cwd:'src/',src: ['image/*'], dest: 'build/'},
			      {expand: true,cwd:'src/',src: ['game.html','home.html','recordGame.html','Serviceagreement.html','agent.html','manager.html','super.html','pokergame.html','pokerrecordgame.html'], dest: 'build/', filter: 'isFile'}
			    ]
			  }
			},
			watch:{
				buildJs:{
					files:['src/js/*.js','src/libs/common/*.js','src/libs/record/*.js','src/libs/sprites/*.js','src/libs/states/*.js','src/libs/main/*.js','src/libs/poker/states/*.js','src/libs/poker/common/*.js','src/libs/poker/main/*.js','src/libs/poker/sprites/*.js'],		
					tasks:['uglify'],
					options:{spawn:false}
				},
				buildResource:{
					files:['src/image/**','src/resources/**','src/game.html','src/home.html','src/recordGame.html','src/Serviceagreement.html','src/agent.html','src/manager.html','src/super.html','src/pokergame.html','src/pokerrecordgame.html'],		
					tasks:['copy'],
					options:{spawn:false}
				}
			}
		});
		grunt.loadNpmTasks('grunt-contrib-uglify');
		grunt.loadNpmTasks('grunt-contrib-copy');
		grunt.loadNpmTasks('grunt-contrib-watch');
		grunt.registerTask('default',['uglify','copy','watch']);
};