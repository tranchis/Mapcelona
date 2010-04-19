$(document).ready(function(){
	/* The code here is executed on page load */
	
	/* Converting the slide handles to draggables, constrained by their parent slider divs: */
	
	$('.slider-handle').draggable({
		containment:'parent',
		axis:'x',
		drag:function(e,ui){
			
			/* The drag function is called on every drag movement, no matter how minute */
			
			if(!this.par)
			{
				/* Initializing the variables only on the first drag move for performance */
				
				this.par = $(this).parent();
				this.parWidth = this.par.width();
				this.width = $(this).width();
			}
			
			var ratio = 1-(ui.position.left+this.width)/this.parWidth;
			
			//this.par.siblings().first().html() = 100*ratio;
		}
	});
});