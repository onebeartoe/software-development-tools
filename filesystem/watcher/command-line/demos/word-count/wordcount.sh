
# populate the word count file
# find all text files
#                       count the words in the text files
#                                     remove the line with total words
#                                                write the output to disk
find . -name "*.text" | xargs wc -w | sed '$d' > wc.out

# parse the word count output file
awk -f report.awk wc.out
