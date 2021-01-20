### ekalla kerralla
asenna git

kloonaa tää repo omalle koneelle
```
git clone https://github.com/coodeex/Automation-Systems.git
```
```
cd Automation-Systems
```
tee oma branch ja pysy aina siellä esim:
```
git checkout -b juha
```

### omalla koneella olevan koodin pushaaminen tänne githubii
1. tallenna kaikki muokatut filet ja kato että oot omassa branchissa
```
git status
```
2. lisää filet
```
git add .
```
3. committaa viestin kanssa
```
git commit -m "viesti"
```
4. pushaa omaan branchiin (älä main branchiin)
```
git push origin omabranch
```
5. käy githubissa katsomassa miltä muutos näyttää ja yhdistämässä branch main branchiin


### branchien yhdistäminen
1. olet pushannut omalta koneelta pätkän koodia omaan bränchiin ja haluat sen yhteiseen main branchiin
2. klikkaa githubissa ```<>code``` sivulla kohtaa ```x branches```
3. klikkaa oman branchisi kohdalla ```New pull request```
4. scrollaa sivun alaosaan katsomaan miltä muutos näyttää
5. jos näyttää hyvältä niin paina ```create pull request```<br>
(5.1 Jos tulee This branch has conflicts that must be resolved niin paina ```Resolve conflicts``` ja muokkaa oikeanlaiseksi ja paina ```Mark as resolved``` ja ```Commit merge```)
6. Paina ```Merge pull request``` ja ```Confirm merge```

### main branchissa olevan koodin päivittäminen omalle koneelle
1. ole omassa branchissasi esim:
```
git checkout juha
```
2. "vedä" päivitys main branchista
```
git pull origin main
```
(3. jos tulee ongelma niin se johtuu mitä todennäiköisimmin siitä, että sinun omaa branchia ja main branchia on kehitetty eteenpäin. Tällöin pitää ensin pushata oma koodi ja sitten GitHubissa yhdistää branchit)

### yleistä huomioo vielä
-ollaan omalla koneella omassa branchissa sen takia että ei vahingossa pilata hyvää main branchia<br>
-pushataan omaan branchiin ja pullataan main branchista<br>
-oman pushauksen jälkeen käydään githubissa tekemässä pull request ja yhdistetään main branchiin<br>
-```git status``` komento on aika kätevä kun siitä näkee missä branchissa on ja mihin fileihin on tehnyt muutoksia<br>
