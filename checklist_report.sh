# !/bin/tcsh -f

echo "Number Lines:\t" `find . -name "*java" | xargs wc -l | tail -1 | cut -f2`
echo "Classes:\t" `grep -R "class " --include=*.java . | grep "{" | wc -l`
echo "Abstract:\t" `grep -R "abstract " --include=*.java . | grep "class " | grep "{" | wc -l`
echo "Subclasses:\t" `grep -R " extends " --include=*.java . | wc -l`
#echo "Interfaces:\t" `grep -R "interface " --include=*.java . | grep "{" | wc -l`
#echo "Interfaces Used:" `grep -R " implements " --include=*.java . | wc -l`
echo ""
echo ""
echo "Use of static"
grep -R "static" --include="*.java" . | grep -v final | grep -v private | grep -v main
echo ""
echo ""
echo "Global instance variables"
grep -R "public|protected" --include="*.java" . | grep -v "(" | grep -v "class\|interface\|final" | grep "static" | grep -v "{"
echo ""
echo ""
echo "Non-private instance variables"
grep -R "public\|protected" --include="*.java" . | grep -v "(" | grep -v "class\|interface\|final" | grep -v "static" | grep -v "{"
echo ""
echo ""
echo "Magic values"
grep -R '\sif[^\w]' --include="*.java" . | grep '"' | grep -v '""'
grep -R '\sif[^\w]' --include="*.java" . | grep "'" | grep -v "''"
grep -R '\sif[^\w]' --include="*.java" . | egrep '[3-9]+[^/]'
echo ""
echo ""
echo "Use of Concrete List"
grep -R "public .*\(Array\|Linked\)List<.*>" --include="*.java" .
echo ""
echo ""
echo "Use of Concrete Map"
grep -R "public .*\(Tree\|Hash\)Map<.*,.*>" --include="*.java" .
echo ""
echo ""
echo "Use of Concrete Set"
grep -R "public .*\(Tree\|Hash\)Set<.*>" --include="*.java" . 
echo ""
echo ""
echo "Longest Methods"
~/bin/lineCountJava.perl `find . -name "*.java"` | sort -nr | head -12 | tail -10
echo ""
echo ""
echo "Bad Exception Handling"
grep -R "printStackTrace" --include="*.java" .