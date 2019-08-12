function StateRecordPreload() {
};
StateRecordPreload.dataLoaded = false;
StateRecordPreload.dataResult = true;

StateRecordPreload.prototype = util.inherit(StatePreload.prototype);
StateRecordPreload.prototype.constructor = StateRecordPreload;

StateRecordPreload.prototype.update = function(){
	if(!StateRecordPreload.dataResult){
		alert('数据加载异常!'); 
		return ;
	}
	if(StateRecordPreload.dataLoaded){
		game.state.start('game');
	}
}
	