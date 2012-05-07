   jQuery(document).ready(function($) {
   
      //replace this URL with whatever URL you want shared.
      var url = 'http://www.dashreports.org';
      //uncomment the line below and comment out the above to set the URL to the current URL
      //var url = window.location;
 
      //replace this with your facebook app ID
      var facebook_app_id = '232402360199709';
     
      var $target = $("#social_target");
     
      //add loading class
      $target.addClass("socprog_loading");
     
      var $ul = $('<ul class="socialbuttons"></ul>');
     
      $target.append($ul);
     
      //add facebook HTML
      var $facebook_li = $('<li/>')
       .append('<iframe src="http://www.facebook.com/plugins/like.php?app_id='+facebook_app_id+'&amp;href='+encodeURIComponent(url)+'&amp;send=false&amp;layout=button_count&amp;width=90&amp;show_faces=false&amp;action=like&amp;colorscheme=light&amp;font&amp;height=21" scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:90px; height:21px;" allowTransparency="true"></iframe>')
       .appendTo($ul);
     
      var tweet = 'Dash Reports - An open-source and light-weight reporting solution';
      var twitter = 'dashreports';
 
      //add tweet HTML
      var $twitter_li = $('<li/>')
        .hide()
        .append('<a href="http://twitter.com/share" class="twitter-share-button" data-url="'+url+'" data-text="'+tweet+'" data-count="horizontal" data-via="'+twitter+'">Tweet</a>')
        .appendTo($ul);
   
      //load twitter
      $.getScript('http://platform.twitter.com/widgets.js', function() {
        $twitter_li.show(); //show it
      });
     
      //add google+1 HTML
      var $google_li = $('<li/>')
        .hide()
        .append('<g:plusone size="medium" href="'+url+'" count="true"></g:plusone>')
        .appendTo($ul);
     
      // Load Google Plus
      $.getScript('https://apis.google.com/js/plusone.js', function() {
        $google_li.show();
      });
     
         
      var $linked_li = $('<li/>')
        .hide()
        .append('<scr'+'ipt type="in/share" data-url="'+url+'" data-counter="right"></scr'+'ipt>')
        .appendTo($ul);
     
      // Load LinkedIn
      $.getScript('http://platform.linkedin.com/in.js', function() {
        $linked_li.show();
      });
    });
   