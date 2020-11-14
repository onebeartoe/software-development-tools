
# populate the word count file
find . -name "*.text" | xargs wc -w | sed '$d' > wc.out

# parse the word count file
awk -f report.awk wc.out
