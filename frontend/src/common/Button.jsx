import { Children, useState } from 'react';
import './Button.css'

export default function Button({ children, icon, style, hover}) {

    const [isHover, setIsHover] = useState(false);
    const handleMouseEnter = () => {
        setIsHover(true)
    };
    const handleMouseLeave = () => {
        setIsHover(false)
    }
    var styleDecouple, hoverDecouple
    if (style) {
         styleDecouple = {
            "color": style[0]
            , "background-color": style[1]
        }
    } else {
        styleDecouple = null
    }
    if (hover) {
        hoverDecouple = {
            "color": hover[0]
            , "background-color": hover[1]
        }
    } else {
        hoverDecouple = null
    }
    return (
        <div id="button-wrapper"
            style={isHover ? hoverDecouple : styleDecouple}
            onMouseEnter={handleMouseEnter}
            onMouseLeave={handleMouseLeave}>
            {!!icon && icon}
            <span id="button-text">{children}</span>
        </div>
    )
}