import { Children, useState } from 'react';
import './Button.css'

export default function Button({ children, className,
                                    onClick, icon, 
                                    style, hover}) {

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
            , "border": style[2]
        }
    } else {
        styleDecouple = null
    }
    if (hover) {
        hoverDecouple = {
            "color": hover[0]
            , "background-color": hover[1]
            ,"border": hover[2] || style[2]
        }
    } else {
        hoverDecouple = null
    }
    return (
        <div id="button-wrapper" className={className}
            style={isHover ? hoverDecouple : styleDecouple}
            onClick={onClick}
            onMouseEnter={handleMouseEnter}
            onMouseLeave={handleMouseLeave}>
            {!!icon && icon}
            <span id="button-text">{children}</span>
        </div>
    )
}