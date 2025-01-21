import './izvodac.css'

export default function Izvodac({ izvodac }) {
    return (
        <div className="izvodac-wrapper">
            {console.log(izvodac)}
            <div className="izvodac-foto-wrapper"><img src={izvodac["fotoIzvodaca"]} alt="" /></div>
            <span className="ime-prezime">{izvodac["imeIzvodaca"]} {izvodac["prezimeIzvodaca"]}</span>
            <span>{izvodac["idZanr"]}Hip hop</span>  
        </div>
    )
}