# Author: SR16/2022 Radić Ognjen
# Project is built in JavaSE17,Swing for GUI,
# Links for UML Model img and mdj files
- Link [UML Model mdj file](https://drive.google.com/file/d/1_sqnM9SIQjcqYBbvmMbOC7exUplZzW4q/view?usp=sharing)
- Link [UML Model img](https://drive.google.com/file/d/1vm-F5uOVVdj7vOXLz2Nqdg1AKYd37Lbv/view?usp=share_link)

#Instructions for Application
##Login window is in file address: src/main/AgencijaLoginWindow.java
##UML Model is in src/model/
##Main functionality for changing userdata in csv file is in admin window that is on this location: src/main/AgencijaAdministratorWindow.java
###When you login in with admin account:milan123 and Sifra54321 you can create,delete,change current data of tourists,Administrators and Agents 
###All passwords are encrypted and stored in userdata.csv(pasword and salt are converted string->byte->hex then hex>byte>auth function) in src/data/ folder so passwords are not readable in whole application you can't access even if you want to I couldn't
###Because of that aAdministrator can't see users password but he can change it , I've made it this way so that Admin would not be able to make reservation as a Tourist