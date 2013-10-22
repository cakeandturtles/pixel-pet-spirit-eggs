var mainWorkerUpdate = function(){
	postMessage(0);
	setTimeout("mainWorkerUpdate()", 60);
};

mainWorkerUpdate();