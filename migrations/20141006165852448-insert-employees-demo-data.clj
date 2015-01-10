;; migrations/20141006165852448-insert-employees-demo-data.clj

(defn up []
  [
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Kadie',Green',kadiegreen@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Scott',Lord',scottlord@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('David',Sharpe',davidsharpe@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('John',Slater',johnslater@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Duane',Dobson',duanedobson@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Ashley ',Mcmahon',ashleymcmahon@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Jonathan',Lomax',jonathanlomax@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Beth',Lang',bethlang@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Mathew',Dobson',mathewdobson@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Jade',Carter',jadecarter@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Adam',Duckett',adamduckett@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('James',Almond',jamesalmond@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Sam',Berry',samberry@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Anthony',Glynn',AnthonyGlynn@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Hannah',Clarke',hannahclarke@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Mark',Bottomley',markbottomley@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Charlotte',Harris',charlotteharris@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Emily',Howard',emilyhoward@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Tom',Wilkinson',tomwilkinson@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Nick',Stansfield',nickstansfield@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Martin',Rue',martinrue@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Peter',Wrigley',peterwrigley@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Simon',Wrigley',simonwrigley@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Ben',Jones',benjones@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Lisa',Young',lisayoung@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Daniel',Ormisher',danielormisher@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('David',Lawson',davidlawson@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Ian',Richards',ianrichards@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Peter',Storey',peterstorey',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Simon',Lomax',SimonLomax@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('David',Swindells',DavidSwindells@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Chris',Lamb',chrislamb@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Matthew',Allingham',mathewalligham@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Steven',Hickey',stevenhickey@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Chris',Kane',chriskane@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Tony',Bury',tonybury@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Dean',Morgan',deanmorgan@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Dorinda',Slater',dorindaslater@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Gemma',Caparelli',gemmacaparelli@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Jade',Henderson',jadehenderson@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Stephen',Edwards',stephenedwards@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Mark',Gilbertson',markgilbertson@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Andrew',Gardiner',andrewgardiner@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Philip',Greenall',philgreenall@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Martin',Sweet',martinsweet@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Alan',Greensmith',alangreensmith@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Susana',Gonzalez',susanagonzalez@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Rebecca',Swarbrick',rebeccaswarbrick@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Dan',Barrett',danielbarrett@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Tony ',Di Gaetano',tonydigaetano@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Kieran',Ryan',kieranryan@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Paul',Young',paulyoung@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Phil',McKeown',philmckeown@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Michael',Hawthornthwaite',michaelhawthornthwaite@ekmsystems.co."uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Marc',McKeown',marcmckeown@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Sean',Hutchinson',seanhutchinson@ekmsystems.co.uk',NULL,NULL,16",1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Carol',Wilkinson',carolwilkinson@ekmsystems.co.uk',NULL,NULL,16",1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Dorothy',Ward',dorothyward@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Vivienne',Robinson',viviennerobinson@ekmsystems.co.uk',NULL,N"UL"L,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Annette',Walker',annettewalker@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Daniel ',Barrett',danielbarrett@ekmsystems.co.uk',NULL,NULL,16,1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Kirstie',Scott',kirstiescott@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Susan',Fenwick',susanfenwick@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Robert',Cunliffe',robertcunliffe@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Sam',Clegg',samclegg@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Charlotte',McConnell',charlottemcconnell@ekmsystems.co."uk',NULL",NULL,16,1");"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Ryan',Witchell',ryanwitchell@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Adam',Waring',adamwaring@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Janet',Westbrook',janetwestbrook@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employees (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Jacqueline',Denwer-Nicholas',jacquelinedn@ekmsystems.co."uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Kayleigh',Hubbard',kayleighhubbard@ekmsystems.co.uk',NUL"L,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Phillip',Rhodes',philrhodes@ekmsystems.co.uk',NULL,NULL,"16,1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Blake',Stone',blakestone@ekmsystems.co.uk',NULL,NULL,16,"1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Scott',Horrocks',scotthorrocks@ekmsystems.co.uk',NULL,NU"LL,16,1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Oliver',Scott',oliverscott@ekmsystems.co.uk',NULL,NULL,1"6,1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Ian',Gibson',iangibson@ekmsystems.co.uk',NULL,NULL,16,1)";
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('David',Blackham',davidblackham@ekmsystems.co.uk',NULL,NU"LL,16,1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Jason',Rothwell',jasonrothwell@ekmsystems.co.uk',NULL,NU"LL,16,1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Steven',Palmer',stevenpalmer@ekmsystems.co.uk',NULL,NULL",16,1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Julia',Gatehouse',juliagatehouse@ekmsystems.co.uk',NULL,"NULL,16,1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Janine',Fawcett',janinefawcett@ekmsystems.co.uk',NULL,NU"LL,16,1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Melisa',Young',melisayoung@ekmsystems.co.uk',NULL,NULL,1"6,1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Steven',Palmer',stevenpalmer@ekmsystems.co.uk',NULL,NULL",16,1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Graham',Shaw',grahamshaw@ekmsystems.co.uk',NULL,NULL,16,"1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Jaxey ',Lewis',jaxeylewis@ekmsystems.co.uk',NULL,NULL,16",1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Ivan',Reyes',ivanreyes@ekmsystems.co.uk',NULL,NULL,16,1)";
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Daniel',Llevo',danielllevo@ekmsystems.co.uk',NULL,NULL,1"6,1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Darren',Tomkinson',darrentomkinson@ekmsystems.co.uk',NUL"L,NULL,16,1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Brahim ',Sersou',brahimsersou@ekmsystems.co.uk',NULL,NUL"L,16,1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Jaxey',Lewis',jaxeylewis@ekmsystems.co.uk',NULL,NULL,16,"1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Miriam',Janigova',miriamjanigova@ekmsystems.co.uk',NULL,"NULL,16,1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('James',Booker',jamesbooker@ekmsystems.co.uk',NULL,NULL,1"6,1);
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Marlene',Bowerbank-Delaveau',marlenebowerbank-delaveau@e"kmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Carlos',Garcia',carlosgarcia@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Nicolas',Douzal',nicolasdouzal@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Josh',Rios-Street',josh@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Marcus',Farran',marcusfarran@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Kieran',Ryan',kieranryan@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('James',Lago',jameslago@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Adrian',Sutcliffe',adriansutcliffe@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Ethan',Sterling',ethansterling@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Matthew',Steele',matthewsteele@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Gillian',Riding',gillianriding@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Gary',Nicholson',garynicholson@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Thomas',Talbot',thomastalbot@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Stephen',Howells',stephenhowells@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Susana',Gonzalez',susanagonzalez@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Sonke',Dokel',sonkedokel@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Dan',Smith',dansmith@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Alex',Joint',alexjoint@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Zoe',Healey',zoehealey@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Craig',Smith',craigsmith@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Daniel',Ford',danielford@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Richard',Ayris',richardayris@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Juan',Munoz',juanmunoz@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Nina',Rios',ninarios@ekmsystems.co.uk',NULL,NULL,16,1);"
"insert into employee"s (firstname,lastname,email,startdate,enddate,departments_id,managerid) values ('Adrian',Ruiz',adrianruiz@ekmsystems.co.uk',NULL,NULL,16,1);"   ])

(defn down []
  [])
