import { useContext, useEffect, useState } from "react"
import { Context } from "./App"
import ScaleLoader from "react-spinners/ScaleLoader"
import { Link, useLocation, Navigate } from 'react-router-dom'
import { FaTicketAlt, FaHome } from "react-icons/fa"
import './Popup.css'
import LinkWrapper from "./common/LinkWrapper"
import Button from "./common/Button"
import axiosPrivate from "./api/axiosPrivate"
import axios from "axios"


export default function Popup() {
    const [message, setMessage] = useState("Transakcija uspješno provedena")
    const location = useLocation()
    if (!location || !location.state || location.state.sentFrom !== "listing") {
        return <Navigate to="/"></Navigate>
    } 
    const { transactionPending, setTransactionPending } = useContext(Context)
    useEffect(() => {
        setTransactionPending(true)
        try {
            const res = axiosPrivate.get(`/preference/oglasi/kupi/${location.state.idOglasa}`)
            console.log("succesfully bought ticket", location.state.idOglasa)
            setTimeout(() => {
                setTransactionPending(false)
            }, Math.floor(Math.random() * 3000) + 2000)
        } catch(err) {
            setTransactionPending(false)
            setMessage("Transakcija neuspješna")
        }
    }, [])
    
    return (
        <div className="popup-container">
            {
                transactionPending ? <>
                    <span className="description">Vaša transakcija se obrađuje</span>
                    <ScaleLoader
                        height={100}
                        radius={15}
                        width={10}
                        margin={4}
                        color='#425dff'
                    />
                </>
                    : <div className="message-container">
                        <span className="description" id="description-success">{message}</span>
                        <LinkWrapper to={"/userUlaznice"}>
                            <Button icon={<FaTicketAlt />}
                                style={["#4b66fc", null]} hover={["white", "#4b66fc"]}>
                                Moje ulazice
                            </Button>
                        </LinkWrapper>
                        <LinkWrapper to={"/"}>
                            <Button icon={<FaHome />}
                                style={["black", null]} hover={["white", "black"]}>
                                Početna stranica
                            </Button>
                        </LinkWrapper>
                    </div>
            }
        </div>
    )

}