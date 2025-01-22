import { useContext, useEffect, useState } from "react"
import { Context } from "./App"
import ScaleLoader from "react-spinners/ScaleLoader"
import { Link } from 'react-router-dom'
import { FaTicketAlt, FaHome } from "react-icons/fa"
import './Popup.css'

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
                transactionPending ? <ScaleLoader
                    height={100}
                    radius={15}
                    width={10}
                    margin={4}
                    color='#425dff'
                />
                    : <div className="message-container">
                        <span className="description">Transakcija uspješno provedena</span>
                        <Link to="/userUlaznice" className="link">
                            <div className="link-flex">
                               <div className="link-container">
                                    <FaTicketAlt /> 
                                    <span className="link-to-tickets">{"Moje ulaznice"}</span>
                               </div>
                            </div>
                        </Link>
                        <Link to="/" className="link">
                            <div className="link-flex">
                               <div className="link-container">
                                    <FaHome /> 
                                    <span className="link-to-tickets">{"Početna stranica"}</span>
                               </div>
                            </div>
                        </Link>
                    </div>
            }
        </div>
    )

}