import { useContext, useEffect, useState } from "react"
import { Context } from "./App"
import ScaleLoader from "react-spinners/ScaleLoader"
import { Link } from 'react-router-dom'
import { FaTicketAlt, FaHome } from "react-icons/fa"
import './Popup.css'
import LinkWrapper from "./common/LinkWrapper"
import Button from "./common/Button"


export default function Popup() {
    const { transactionPending, setTransactionPending } = useContext(Context)
    useEffect(() => {
        setTransactionPending(true)
        setTimeout(() => {
            setTransactionPending(false)
        }, Math.floor(Math.random() * 3000) + 2000)
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
                        <span className="description" id="description-success">Transakcija uspješno provedena</span>
                        <LinkWrapper to={"/userUlaznice"}>
                            <Button icon={<FaTicketAlt />}
                                style={["#4b66fc", null]} hover={["white", "#4b66fc"]}>
                                Moje ulazice
                            </Button>
                        </LinkWrapper>
                        <LinkWrapper to={"/"}>
                            <Button icon={<FaHome />}
                                style={["#4b66fc", null]} hover={["white", "#4b66fc"]}>
                                Početna stranica
                            </Button>
                        </LinkWrapper>
                    </div>
            }
        </div>
    )

}