const statusTransakcijeMap = {
  "CEKA_POTVRDU": {
    "display": "Čeka potvrdu"
    ,"color": "#308614"
  },
  "PROVEDENA": {
    "display": "Provedena"
    ,"color": "#9B771C"
  },
  "ODBIJENA": {
    "display": "Odbijena",
    "color": "#920909"
  }
}

function statusTranskacijeColor(status) {
  switch (status) {
    case "CEKA_POTVRDU": 
      return {"color": "#9B771C"}
    case "PROVEDENA":
      return {"color": "#308614"}
    case "ODBIJENA":
      return {"color": "#920909"}
    default:
      return {}
  }
}

export { statusTransakcijeMap, statusTranskacijeColor }