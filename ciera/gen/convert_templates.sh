rm -rf ../src/java/*
MCMC_DIR=/media/sf_levi/git/mc/mcmc/arlan
filelist=`find ../templates -name t.*.java`
for f in $filelist; do
  DIR=`dirname $f`
  mkdir -p ../src/java/${DIR:12}
  cat  $f | sed "s/^.assign[^\"]*\"\(.*\)\"/\1/" | \
  $MCMC_DIR/template_engine | \
  awk '{if ($0 ~ "if ") {gsub(" and "," \\&\\& ");gsub(" or ", " || ");gsub(" not "," ! ");} print $0;}' | \
  sed 's/\(T_b[^"]*\)"\(.*\)"\([^"]*\)/\1\&quot;\2\&quot;\3/' | \
  awk '{if ($0 ~ "T_b") gsub("\"","\\\"");print $0;}' | \
  sed 's/&quot;/"/g' | \
  sed 's/ \("[^"]*"\) == \([^ ]*\) / 0==strcmp(\1,\2) /g' | \
  sed 's/ \("[^"]*"\) != \([^ ]*\) / 0!=strcmp(\1,\2) /g' > ../src/java/${f:12}
done
