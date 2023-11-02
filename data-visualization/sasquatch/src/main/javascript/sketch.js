// Daniel Shiffman
// http://codingtra.in
// http://patreon.com/codingtrain

// Subscriber Mapping Visualization
// https://youtu.be/Ae73YY_GAU8

// Sasquatch sightings update by Roberto Marquez

let sasquatchData;

const mappa = new Mappa('Leaflet');
let trainMap;
let canvas;

let data = [];

const options = 
{
  lat: 40,
  lng: -95,
  zoom: 3.5,
  style: "http://{s}.tile.osm.org/{z}/{x}/{y}.png"
}

function preload() {
  sasquatchData = loadTable('sightings.csv', 'header');
}

function setup() 
{
  canvas = createCanvas(800, 600);
  trainMap = mappa.tileMap(options);
  trainMap.overlay(canvas);

  let maxSubs = 0;
  let minSubs = Infinity;

  for (let row of sasquatchData.rows) 
  {
    let country = row.get('state').toLowerCase();
    
      let lat = row.get('latitude');
      let lon = row.get('longitude');

      let count = Number(row.get('count'));
      
      data.push(
      {
        lat,
        lon,
        count
      });

      if (count > maxSubs) 
      {
        maxSubs = count;
      }

      if (count < minSubs) 
      {
        minSubs = count;
      }
  }

  let minD = sqrt(minSubs);
  let maxD = sqrt(maxSubs);

  for (let country of data) 
  {
    country.diameter = map(sqrt(country.count), minD, maxD, 1, 6);
  }

  //console.log(data)
}

function draw() 
{
  clear();
  
  fill(0, 0, 0);
  textFont('Courier New');
  textSize(24);
  let heading1 = "Sasquatch Sightings\n" ;
  
  let heading2 = 
        "start date: 1869-11-10 \n" + 
        "end date: 2023-02-28 \n " + 
        "total sightings: 5082 \n ";
        
  
  let heading2_b = "unparsable sighting dates: 978";
  
  let heading3 = "https://github.com/onebeartoe/software-development-tools/\n         tree/master/data-visualization/sasquatch";
  
  let x = width / 2.0 - (textWidth(heading1) / 2.0);
  let y = 25;
  text(heading1, x, y);
  
  y += 25;
  x -= 35;
  text(heading2, x, y);
  
  text(heading2_b, 160, 140);
  
  textSize(20);
  x -= 195;
  y += 500;
  text(heading3, x, y);
  
  for (let country of data) 
  {
    const pix = trainMap.latLngToPixel(country.lat, country.lon);
    fill(frameCount % 255, 0, 200, 100);
    const zoom = trainMap.zoom();
    
    const scl = pow(2, zoom);
//    const scl = pow(2, zoom) * sin(frameCount * 0.1);

    ellipse(pix.x, pix.y, country.diameter * scl);
  }
}
