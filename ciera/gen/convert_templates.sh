#
# Still need to hand-edit the following:
#  - embedded double-quotes (")
#
#MCMC_DIR=~/git/xtuml/mc/mcmc/arlan
MCMC_DIR=/media/sf_levi/git/xtuml/mc/mcmc/arlan
filelist=`ls ../templates/t.*.java | xargs -n1 basename`
for f in $filelist; do
  cat  ../templates/$f | sed "s/^.assign[^\"]*\"\(.*\)\"/\1/" | \
  $MCMC_DIR/template_engine | \
  awk '{if ($0 ~ "if ") {gsub(" and "," \\&\\& ");gsub(" or ", " || ");gsub(" not "," ! ");gsub("\\.","->");} print $0;}' | \
  sed 's/\(T_b[^"]*\)"\(.*\)"\([^"]*\)/\1\&quot;\2\&quot;\3/' | \
  awk '{if ($0 ~ "T_b") gsub("\"","\\\"");print $0;}' | \
  sed 's/&quot;/"/g' | \
  sed 's/ \("[^"]*"\) == \([^ ]*\) / 0==strcmp(\1,\2) /g' | \
  sed 's/ \("[^"]*"\) != \([^ ]*\) / 0!=strcmp(\1,\2) /g' > ../src/java/$f
done
