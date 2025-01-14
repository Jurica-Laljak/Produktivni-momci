import React, { useEffect, useState } from 'react';
import "./UserReport.css";
import axiosPrivate from "./api/axiosPrivate";
import { FaRegTrashAlt, FaLock  } from 'react-icons/fa';
import { RxCross2 } from "react-icons/rx";
import { statusTransakcijeMap, statusTranskacijeColor } from '../data/statusTransakcijeMap';

export default function UserReport({ korisnik, transakcije, userData, adminRole }) {

    function handleOnClickKorisnik(korisnik) {
        if(confirm(`Jeste li sigurni da želite obrisati korisnika ${korisnik.imeKorisnika} ${korisnik.prezimeKorisnika}?`)) {
            axiosPrivate.delete(`preference/korisnici/izbrisi/${korisnik.idKorisnika}`)
                .then(response => {
                    window.location.reload();
                })
                .catch(error => {
                    console.error('There was an error sending the data:', error);
                });
        }
    }

    function handleOnClickTransakcija(idTransakcije) {
        if(confirm(`Jeste li sigurni da želite obrisati transakciju ${idTransakcije}?`)) {
            axiosPrivate.delete(`transakcije/izbrisi/${idTransakcije}`)
                .then(response => {
                    window.location.reload();
                })
                .catch(error => {
                    console.error('There was an error sending the data:', error);
                });
        }
    }

    function handleOnClickAdmin(idKorisnika) {
        axiosPrivate.get(`korisnici/grant/admin/${idKorisnika}`)
            .then(response => {
                window.location.reload();
            })
            .catch(error => {
                console.error('There was an error sending the data:', error);
            });
    }

    var brojTransakcija = 0
    if (korisnik.transakcijePonudaIds) {
        brojTransakcija += korisnik.transakcijePonudaIds.length
    }
    if (korisnik.transakcijeOglasIds) {
        brojTransakcija += korisnik.transakcijeOglasIds.length
    }

    return (
        <div className="report-container" key={korisnik.idKorisnika}>
            <details>
                <summary className='korisnik-sum'>
                    <div className='korisnik-info' style={{cursor: brojTransakcija ? "pointer" : "default"}}>
                        {korisnik.imeKorisnika} {korisnik.prezimeKorisnika} (ID: {korisnik.idKorisnika}) {
                            brojTransakcija > 0 && <span style={{fontWeight: "bold"}}>
                                - {brojTransakcija} transakcija</span>
                        }
                    </div>
                    <div className='grant-admin'>
                        {korisnik.roleIds.filter((role) => role == adminRole.idRole).length == 0?
                            <button className='btn-grant-admin' onClick={() => handleOnClickAdmin(korisnik.googleId)}>
                                <FaLock/>
                                Učini korisnika administratorom
                            </button>
                            :
                            <div className='div-admin'>
                                {userData.idKorisnika !== korisnik.idKorisnika && 
                                "Korisnik je administrator"}
                            </div>
                        }
                    </div>
                    <div>
                        {userData.idKorisnika !== korisnik.idKorisnika ? <button className='btn-delete-korisnik btn-delete' onClick={() => handleOnClickKorisnik(korisnik)}>
                            <FaRegTrashAlt/>
                            Obriši korsnika</button> : <a href="/admin" className='korisnik-info'>Moj račun</a>}

                    </div>
                </summary>
                {brojTransakcija > 0 ?
                    <div style={{overflow: "auto"}}>
                        <ul class="responsive-table">
                            <li class="table-header">
                                <div class="col col-1">idTransakcije</div>
                                <div class="col col-2">idUlaznicaPonuda</div>
                                <div class="col col-3">idUlaznicaOglas</div>
                                <div class="col col-4">idKorisnikPonuda</div>
                                <div class="col col-5">idKorisnikOglas</div>
                                <div class="col col-6">statusTransakcije</div>
                                <div class="col col-7">idOglas</div>
                                <div class="col col-8">datumTransakcije</div>
                                <div class="col col-9"></div>
                            </li>
                            {transakcije.filter((trans) =>
                                korisnik.transakcijePonudaIds.some((id) => trans.idTransakcije == id) ||
                                korisnik.transakcijeOglasIds.some((id) => trans.idTransakcije == id)).map((tr) =>
                                    <li class="table-row" style={statusTranskacijeColor(tr.statusTransakcije)}>
                                        <div class="col col-1" data-label="idTransakcije">{tr.idTransakcije}</div>
                                        <div class="col col-2" data-label="idUlaznicaPonuda">{tr.idUlaznicaPonuda}</div>
                                        <div class="col col-3" data-label="idUlaznicaOglas">{tr.idUlaznicaOglas}</div>
                                        <div class="col col-4" data-label="idKorisnikPonuda">{tr.idKorisnikPonuda}</div>
                                        <div class="col col-5" data-label="idKorisnikOglas">{tr.idKorisnikOglas}</div>
                                        <div class="col col-6" data-label="statusTransakcije">{statusTransakcijeMap[tr.statusTransakcije].display}</div>
                                        <div class="col col-7" data-label="idOglas">{tr.idOglas ? tr.idOglas : <RxCross2 />}</div>
                                        <div class="col col-8" data-label="datumTransakcije">{new Date(tr.datumTransakcije).toLocaleDateString('hr-HR')}</div>
                                        <div class="col col-9"><button className='btn-delete-transakcija btn-delete' onClick={() => handleOnClickTransakcija(tr.idTransakcije)}>
                                            <FaRegTrashAlt/>
                                            Obriši transakciju
                                        </button></div>
                                    </li>
                                )}
                        </ul>
                    </div>
                    : <></>}

            </details>
        </div>
    );
}