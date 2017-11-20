<%@include file="../common/JSTL_tags.jsp" %> 

<!DOCTYPE html>
<html>
<head>
	<title>Fancy Jquery</title>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	
	<!-- Add jQuery library -->
	<script type="text/javascript" src="<c:url value='/static/resources/fancybox-2.1.7/lib/jquery-1.10.2.min.js'/>"></script>
	
	<!-- Add mousewheel plugin (this is optional) -->
	<script type="text/javascript" src="<c:url value='/static/resources/fancybox-2.1.7/lib/jquery.mousewheel.pack.js'/>"></script>
	
	<!-- Add fancyBox main JS and CSS files -->
	<script type="text/javascript" src="<c:url value='/static/resources/fancybox-2.1.7/source/jquery.fancybox.js'/>"></script>
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/resources/fancybox-2.1.7/source/jquery.fancybox.css'/>" media="screen" />
	
	<!-- Add Button helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/resources/fancybox-2.1.7/source/helpers/jquery.fancybox-buttons.css'/>" />
	<script type="text/javascript" src="<c:url value='/static/resources/fancybox-2.1.7/source/helpers/jquery.fancybox-buttons.js'/>"></script>
	
	<!-- Add Thumbnail helper (this is optional) -->
	<link rel="stylesheet" type="text/css" href="<c:url value='/static/resources/fancybox-2.1.7/source/helpers/jquery.fancybox-thumbs.css'/>" />
	<script type="text/javascript" src="<c:url value='/static/resources/fancybox-2.1.7/source/helpers/jquery.fancybox-thumbs.js'/>"></script>
	
	<!-- Add Media helper (this is optional) -->
	<script type="text/javascript" src="<c:url value='/static/resources/fancybox-2.1.7/source/helpers/jquery.fancybox-media.js'/>"></script>
	
	
	
	<!--  For videos -->
	<script src="http://www.youtube.com/player_api"></script>


<link rel="stylesheet" href="http://cdn.kendostatic.com/2014.3.1316/styles/kendo.common.min.css">
<link rel="stylesheet" href="http://cdn.kendostatic.com/2014.3.1316/styles/kendo.rtl.min.css">
<link rel="stylesheet" href="http://cdn.kendostatic.com/2014.3.1316/styles/kendo.default.min.css">
<link rel="stylesheet" href="http://cdn.kendostatic.com/2014.3.1316/styles/kendo.dataviz.min.css">
<link rel="stylesheet" href="http://cdn.kendostatic.com/2014.3.1316/styles/kendo.dataviz.default.min.css">
<link rel="stylesheet" href="http://cdn.kendostatic.com/2014.3.1316/styles/kendo.mobile.all.min.css">
<!-- <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script> -->

<script src="http://cdn.kendostatic.com/2014.3.1316/js/kendo.all.min.js"></script>





<script type="text/javascript">
	
//Fires when the player's state changes.
function onPlayerStateChange(event) {
    // Go to the next video after the current one is finished playing
    if (event.data === 0) {
        $.fancybox.next();
    }
}

// The API will call this function when the page has finished downloading the JavaScript for the player API
function onYouTubePlayerAPIReady() {
    
    // Initialise the fancyBox after the DOM is loaded
    $(document).ready(function() {
        $(".fancybox")
            .attr('rel', 'gallery')
            .fancybox({
                openEffect  : 'none',
                closeEffect : 'none',
                nextEffect  : 'none',
                prevEffect  : 'none',
                padding     : 0,
                margin      : 50,
                beforeShow  : function() {
                    // Find the iframe ID
                    var id = $.fancybox.inner.find('iframe').attr('id');
                    
                    // Create video player object and add event listeners
                    var player = new YT.Player(id, {
                        events: {
                            'onReady': onPlayerReady,
                            'onStateChange': onPlayerStateChange
                        }
                    });
                }
            });
    });
    
}
</script>
	<script type="text/javascript">
		$(document).ready(function() {
			/*
			 *  Simple image gallery. Uses default settings
			 */

			$('.fancybox').fancybox();

			/*
			 *  Different effects
			 */

			// Change title type, overlay closing speed
			$(".fancybox-effects-a").fancybox({
				helpers: {
					title : {
						type : 'outside'
					},
					overlay : {
						speedOut : 0
					}
				}
			});

			// Disable opening and closing animations, change title type
			$(".fancybox-effects-b").fancybox({
				openEffect  : 'none',
				closeEffect	: 'none',

				helpers : {
					title : {
						type : 'over'
					}
				}
			});

			// Set custom style, close if clicked, change title type and overlay color
			$(".fancybox-effects-c").fancybox({
				wrapCSS    : 'fancybox-custom',
				closeClick : true,

				openEffect : 'none',

				helpers : {
					title : {
						type : 'inside'
					},
					overlay : {
						css : {
							'background' : 'rgba(238,238,238,0.85)'
						}
					}
				}
			});

			// Remove padding, set opening and closing animations, close if clicked and disable overlay
			$(".fancybox-effects-d").fancybox({
				padding: 0,

				openEffect : 'elastic',
				openSpeed  : 150,

				closeEffect : 'elastic',
				closeSpeed  : 150,

				closeClick : true,

				helpers : {
					overlay : null
				}
			});

			/*
			 *  Button helper. Disable animations, hide close button, change title type and content
			 */

			$('.fancybox-buttons').fancybox({
				openEffect  : 'none',
				closeEffect : 'none',

				prevEffect : 'none',
				nextEffect : 'none',

				closeBtn  : false,

				helpers : {
					title : {
						type : 'inside'
					},
					buttons	: {}
				},

				afterLoad : function() {
					this.title = 'Image ' + (this.index + 1) + ' of ' + this.group.length + (this.title ? ' - ' + this.title : '');
				}
			});


			/*
			 *  Thumbnail helper. Disable animations, hide close button, arrows and slide to next gallery item if clicked
			 */

			$('.fancybox-thumbs').fancybox({
				prevEffect : 'none',
				nextEffect : 'none',

				closeBtn  : false,
				arrows    : false,
				nextClick : true,

				helpers : {
					thumbs : {
						width  : 50,
						height : 50
					}
				}
			});

			/*
			 *  Media helper. Group items, disable animations, hide arrows, enable media and button helpers.
			*/
			$('.fancybox-media')
				.attr('rel', 'media-gallery')
				.fancybox({
					openEffect : 'none',
					closeEffect : 'none',
					prevEffect : 'none',
					nextEffect : 'none',

					arrows : false,
					helpers : {
						media : {},
						buttons : {}
					}
				});

			/*
			 *  Open manually
			 */

			$("#fancybox-manual-a").click(function() {
				$.fancybox.open('1_b.jpg');
			});

			$("#fancybox-manual-b").click(function() {
				$.fancybox.open({
					href : 'iframe.html',
					type : 'iframe',
					padding : 5
				});
			});

			$("#fancybox-manual-c").click(function() {
				$.fancybox.open([
					{
						href : '1_b.jpg',
						title : 'My title'
					}, {
						href : '2_b.jpg',
						title : '2nd title'
					}, {
						href : '3_b.jpg'
					}
				], {
					helpers : {
						thumbs : {
							width: 75,
							height: 50
						}
					}
				});
			});


		});
	</script>

</head>

<body>

<!-- Don't worry about this error, whoever includes this should end the div tag -->

	