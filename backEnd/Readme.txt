Predpoklad: ak užívateľ zadá zlý vstup v akejkoľvek forme (či už vo vstupnom súbore alebo riadkom v konzole) program pobeží ďalej ignorujúc zlý vstup, pričom užívateľ bude upozornený príslušnou hláškou...

Predpoklad: ako end-of-line znak som neustále využíval/považoval ("\n" --štandart java, ako riešenie pre všetky platformy je vhodnejšie využiť static System.lineSeparator() ktory vráti sekvenciu znakov pre end-of-line).

program sa štartuje  cez kozolu (v linuxe príkazom ) :
java -jar programmName.jar start

kde programmName je názov jar archívu(binárneho java archívu) - konkretne teda PaymentTrackerSolution-Runnable.jar

doplňujúce parametre po start sú (v ľubovoľnom poradí, môžu a nemusia byť prítomné):
"-rates" "cesta/k/suboru/obsahujúcemu/meny/a/pomery" (pomery: koľko jednotiek za 1 dolar)
"-input" "cesta/k/suboru/obsahujúcemu/meny/a/platby"

použitá linux/unix konvencia s parametrami, parameter start je ale povinný

pre ukončenie programu musí uživateľ napísať "quit" !!! a potvrdiť enterom !!!

počas behu programu užívateľ vkladá platby vo formáte:
mena platba(hodnota)

menu je string (reťazec)
platba je long (celé číslo)

užívateľ počas behu programu môže pridať nový pomer pre novú menu a to formátom
"-rate" mena pomer

"-rate" je povinný parameter
mena je string(reťazec)
pomer je double(desatinné číslo vo formáte  java, teda "2542.234", akýkoľvek iný formát je odmietnutý)

vstupné platby(hodnoty) som načítaval ako long - celočíselne (takto to bolo naznačené aj v úlohe) avšak ak boly platby v desatinných čislach (hodnoty), tak program by mohol byť stále korektný, len by bola potrebná drobná úprava pomocou funcií Double: Double.doubleToLongBits() (načítanie double a jeho prerobenie na long - interne práca s long) a Double.longBitsToDouble() (prerobenie interného long na double a vypísanie double do konzoli - platba(hodnota)). Poznámka, Java verzie 8 už vraj() podporuje aj AtomicDouble takže aj to by mohlo byť

Kód som vyvýjal pomocou eclipsu.
Testy som spravil ako samostatný projekt ktorý mal referenciu na pôvodný projekt,
no ak priložíte jar ako knižnicu, možno to bude fungovať aj takto, určite to bude ale fungovať ak importujete obe projekty do eclipsu a pridáte pre test projekt závislosť na solution projekte



