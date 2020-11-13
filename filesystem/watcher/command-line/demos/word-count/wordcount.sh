
# populate the word count file
find . -name "*.text" | xargs wc -w | sed '$d' > wc.out
#find . -name "*.text" | xargs wc -l | sed '$d' > wc.out
#wc -l *.text > wc.out

# parse the word count file
awk '{if($1 > 10) {print "over: ", $0; print "something else";} else print "good: " , $0;}' wc.out
