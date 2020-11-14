{
    if($1 > 10) 
    {
        print "over: ", $0; 
        system("sleep 4");
        "play whomp.mp3 " | getline result
        print result
        print "over played";
    } 
    else
    {
        print "good: " , $0;
        system("sleep 4");
        "play poing.mp3 " | getline result
        print result
        print "good played"
    }
}
