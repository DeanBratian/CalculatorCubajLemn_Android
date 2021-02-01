# CalculatorCubajLemn_Android

Functionalitate:
- cubaj lemn rotund (utilizand formula ariei cilindrului) pe 2 spatii de stocare, cu diferite specii, adaugare multipla, adjustari diametre etc.
- generare raport sortat dupa specie-lungime-diametru cu export .csv catre Drive diferite aplicatii de transfer.
- raportul este generat comform legilor silvice in vigoare: 
->piesele identice cu diametrul mai mic decat 20 pot fi grupate 
->calculare volume pe specii pentru cele 2 spatii de stocare si numarare piese.

-3 main activities designed using ConstraintLayout:
-	insert data
-	wiew inserted data
-	wiew report

Workflow:
-	entries are stored in a list in insert_data activity (multiple insertion possible)
-	data can be viewed from insert_data activity
-	data can be altered in view_data activity
-	a report will be generated from view_data activity
-	the report can be exported from wiew_report activity

Other: each activity has a menu in the top side of it, entries are saved in shared prefferences so that they are accessible throughout instances of the app and shared prefferences can be deleted.

