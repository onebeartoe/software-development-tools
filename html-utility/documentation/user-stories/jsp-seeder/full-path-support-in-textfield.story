
As a user of JspSeeder from the HtmlUtility,
I need the text input field to support a full path,
so that it parses the 'webapp' substring and creates the content at the value following 'webapp'.

If an absolute path is pasted into the textfield and it contains the 'webapp' substring
    then set the source directory to everything before and including the 'webapp' directory and
         set the  target directory to everything after the 'webapp' directory and
if an absolute path is not pasted into the textfield then execute as before.
