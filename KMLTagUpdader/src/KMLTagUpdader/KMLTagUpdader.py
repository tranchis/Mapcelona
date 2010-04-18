'''
Created on 18/04/2010

@author: igomez
'''

f = open("neighbourhoods.txt")

my_index = 238

template = '''
<!-- MY_DISTRICT_HERE -->
<StyleMap id="MY_INDEX_HERE">
        <Pair>
            <key>normal</key>
            <styleUrl>#MY_INDEX_HEREN</styleUrl>
        </Pair>
        <Pair>
            <key>highlight</key>
            <styleUrl>#MY_INDEX_HEREH</styleUrl>
        </Pair>
    </StyleMap>
<Style id="MY_INDEX_HEREN">
        <IconStyle>
            <scale>1.1</scale>
            <Icon>
                <href>http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png</href>
            </Icon>
            <hotSpot x="20" y="2" xunits="pixels" yunits="pixels"/>
        </IconStyle>
        <LineStyle>
            <color>FAFAFMY_INDEX_HERE</color>
            <width>1.2</width>
        </LineStyle>
        <PolyStyle>
            <color>FAFAFMY_INDEX_HERE</color>
        </PolyStyle>
    </Style>
<Style id="MY_INDEX_HEREH">
        <IconStyle>
            <scale>1.1</scale>
            <Icon>
                <href>http://maps.google.com/mapfiles/kml/pushpin/ylw-pushpin.png</href>
            </Icon>
            <hotSpot x="20" y="2" xunits="pixels" yunits="pixels"/>
        </IconStyle>
        <LineStyle>
            <color>FAFAFMY_INDEX_HERE</color>
            <width>1.2</width>
        </LineStyle>
        <PolyStyle>
            <color>FAFAFMY_INDEX_HERE</color>
        </PolyStyle>
    </Style>
'''

for line in f.readlines():
    line = line.replace("\n","")
    
    template_new_comment = template.replace("MY_DISTRICT_HERE",line)
    
    template_new_id = template_new_comment.replace("MY_INDEX_HERE",str(my_index))
    
    print template_new_id
    
    my_index = my_index + 1
    

