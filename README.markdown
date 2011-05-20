# rosling: Rails On Sling

For the lols.

Rails is http://rubyonrails.org/

Sling is http://sling.apache.org/

# Getting Started

## Start Sling

    mkdir ~/opt/sling
    cd ~/opt/sling
    wget http://download.filehat.com/apache/sling/org.apache.sling.launchpad-6-standalone.jar
    java -server -Xmx1024m -jar org.apache.sling.launchpad-6-standalone.jar

## Install rosling to felix

Assuming sling is running at http://localhost:8080/

    cd ~/code/rosling
    mvn clean install

If sling (felix is included in sling launchpad) is at different location,

    mvn clean install -DfelixUrl=http://blahblah:4502/


## Set up rosling

    http://localhost:8080/system/console/configMgr/rosling.Settings

- templateDir should be fullpath to where templates are (/home/saml/code/rosling/src/main/resources/templates)
- staticUrl is where static files (js, css) are served from.
  You can use filesystem resource provider (see below).

# Create resource

There is rosling.example.blog that implements Servlet that'll handle resources with sling:resourceType = rosling_blog_entry

So, you can create a resource with sling:resourceType = rosling_blog_entry

    curl -u admin:admin -Fjcr:primaryType=nt:unstructured -Fsling:resourceType=rosling_blog_eauthor=saml -Ftitle="hello world" -Ftext="Cheeese" http://localhost:8080/content/test/entry

Then, go to the created resource

    http://localhost:8080/content/test/entry.html

You can use sling explorer to CRUD resources:

    http://localhost:8080/.explorer.html

If not found, you may need to download and install Explorer bundle from sling home page.



## Install FileSystem Resource Provider (optional)

look at http://sling.apache.org/site/accessing-filesystem-resources-extensionsfsresource.html

    wget http://download.filehat.com/apache//sling/org.apache.sling.fsresource-1.0.2.jar
    curl -u admin:admin -Faction=install -F_noredir_=_noredir_ -F=bundlefile=@org.apache.sling.fsresource-1.0.2.jar -Fbundlestart=start -Fbundlestartlevel=20 -FrefreshPackages=true http://localhost:8080/system/console/install





