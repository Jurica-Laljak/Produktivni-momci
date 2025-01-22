import './izvodac.css'
import axios from 'axios'
import { useState, useEffect } from 'react'
import { genreMap } from '../data/genreMap'
import ScaleLoader from 'react-spinners/ScaleLoader'
import { preloadImage } from './utilities/preloadImage'

export default function Izvodac({ izvodac }) {
    const [zanr, setZanr] = useState("")
    const [loading, setLoading] = useState(false)
    console.log("Izvodac:", izvodac)
    useEffect(() => {
        const fetchGenreById = async () => {
            console.log("Zanrid:", izvodac["zanrId"].toString())
            setLoading(true)
            const res = await axios.get(`/api/zanrovi/${izvodac["zanrId"]}`)
            setZanr(genreMap[res.data.imeZanra].name)
            preloadImage(izvodac["fotoIzvodaca"]).then(() => {
                setLoading(false)
            })
        }
        fetchGenreById()
    }, [izvodac["fotoIzvodaca"]])
    return (
        <>
            {
                loading ?
                    <ScaleLoader
                        height={100}
                        radius={15}
                        width={10}
                        margin={4}
                        color='#425DFF'
                    />
                    :
                    <div className="izvodac-wrapper">
                        <div className="izvodac-foto-wrapper"><img src={izvodac["fotoIzvodaca"]} alt="" /></div>
                        <span className="ime-prezime">{izvodac["imeIzvodaca"]} {izvodac["prezimeIzvodaca"]}</span>
                        <span>{zanr || ""}</span>
                    </div>
            }
        </>
    )
}