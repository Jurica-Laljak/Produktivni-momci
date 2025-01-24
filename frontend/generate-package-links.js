import fs from 'fs';

// Učitavanje package.json
const packageJson = JSON.parse(fs.readFileSync('./package.json', 'utf-8'));

// Dohvati sve pakete iz dependencies i devDependencies
const dependencies = {
  ...packageJson.dependencies,
  ...packageJson.devDependencies,
};

// Generiraj popis s poveznicama na npm stranice
const result = Object.keys(dependencies).map((pkg) => {
  return `- [${pkg}](https://www.npmjs.com/package/${pkg})`;
});

// Spremi popis u Markdown datoteku
fs.writeFileSync('C:/Users/hfikret/Desktop/FAKS/PROGI/package-links.md', result.join('\n'));

console.log('✅ Generiran popis paketa u package-links.md');
