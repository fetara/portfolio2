const i = document.querySelector("[data-cursor-dot]")
  , d = document.querySelector("[data-cursor-outline]");
i && d && window.innerWidth > 768 && (window.addEventListener("mousemove", t => {
    const s = t.clientX
      , o = t.clientY;
    i.style.left = `${s}px`,
    i.style.top = `${o}px`,
    d.animate({
        left: `${s}px`,
        top: `${o}px`
    }, {
        duration: 500,
        fill: "forwards"
    })
}
),
document.querySelectorAll("a, button, .btn, .skill-card, .project-card").forEach(t => {
    t.addEventListener("mouseenter", () => {
        i.style.transform = "scale(2)",
        d.style.transform = "scale(1.5)"
    }
    ),
    t.addEventListener("mouseleave", () => {
        i.style.transform = "scale(1)",
        d.style.transform = "scale(1)"
    }
    )
}
));
document.querySelectorAll('a[href^="#"]').forEach(e => {
    e.addEventListener("click", function(t) {
        t.preventDefault();
        const s = document.querySelector(this.getAttribute("href"));
        s && s.scrollIntoView({
            behavior: "smooth",
            block: "start"
        })
    })
}
);
const u = document.querySelector("[data-scroll-progress]");
function p() {
    const e = document.documentElement.scrollHeight - document.documentElement.clientHeight
      , t = window.scrollY / e;
    u && (u.style.transform = `scaleX(${t})`)
}
window.addEventListener("scroll", p);
const c = document.querySelector("[data-mobile-toggle]")
  , r = document.querySelector("[data-mobile-menu]")
  , h = document.querySelectorAll("[data-nav-link]");
if (c && r) {
    let e = function() {
        s > t + 50 && (c.classList.remove("active"),
        r.classList.remove("active"),
        document.body.style.overflow = "")
    };
    c.addEventListener("click", () => {
        c.classList.toggle("active"),
        r.classList.toggle("active"),
        document.body.style.overflow = r.classList.contains("active") ? "hidden" : ""
    }
    ),
    h.forEach(o => {
        o.addEventListener("click", () => {
            c.classList.remove("active"),
            r.classList.remove("active"),
            document.body.style.overflow = ""
        }
        )
    }
    ),
    document.addEventListener("click", o => {
        r.classList.contains("active") && !r.contains(o.target) && !c.contains(o.target) && (c.classList.remove("active"),
        r.classList.remove("active"),
        document.body.style.overflow = "")
    }
    );
    let t = 0
      , s = 0;
    r.addEventListener("touchstart", o => {
        t = o.changedTouches[0].screenX
    }
    , {
        passive: !0
    }),
    r.addEventListener("touchend", o => {
        s = o.changedTouches[0].screenX,
        e()
    }
    , {
        passive: !0
    })
}
window.innerWidth <= 768 && document.querySelectorAll(".btn, .contact-card, .skill-card-inner, .nav-link").forEach(t => {
    t.addEventListener("touchstart", function() {
        this.style.transform = "scale(0.95)"
    }, {
        passive: !0
    }),
    t.addEventListener("touchend", function() {
        setTimeout( () => {
            this.style.transform = ""
        }
        , 150)
    }, {
        passive: !0
    })
}
);
const m = document.querySelector(".nav");
window.addEventListener("scroll", () => {
    window.scrollY > 100 ? m.classList.add("scrolled") : m.classList.remove("scrolled")
}
);
const g = {
    threshold: .1,
    rootMargin: "0px 0px -100px 0px"
}
  , E = new IntersectionObserver(e => {
    e.forEach( (t, s) => {
        t.isIntersecting && setTimeout( () => {
            t.target.style.opacity = "1",
            t.target.style.transform = "translateY(0)"
        }
        , s * 100)
    }
    )
}
,g);
document.querySelectorAll(".skill-card, .project-card, .contact-card, .about-text").forEach(e => {
    e.style.opacity = "0",
    e.style.transform = "translateY(30px)",
    e.style.transition = "opacity 0.8s cubic-bezier(0.4, 0, 0.2, 1), transform 0.8s cubic-bezier(0.4, 0, 0.2, 1)",
    E.observe(e)
}
);
window.addEventListener("scroll", () => {
    const e = window.pageYOffset;
    document.querySelectorAll(".gradient-orb").forEach( (o, n) => {
        const a = .5 + n * .1;
        o.style.transform = `translateY(${e * a}px)`
    }
    ),
    document.querySelectorAll(".float-element").forEach( (o, n) => {
        const a = .3 + n * .05;
        o.style.transform = `translateY(${e * a * -1}px)`
    }
    )
}
);
const l = document.querySelector(".name-gradient");
l && (l.style.opacity = "0",
l.style.transform = "translateY(50px)",
setTimeout( () => {
    l.style.transition = "all 1s cubic-bezier(0.4, 0, 0.2, 1)",
    l.style.opacity = "1",
    l.style.transform = "translateY(0)"
}
, 300));
const L = document.querySelectorAll(".btn");
L.forEach(e => {
    e.addEventListener("mousemove", t => {
        const s = e.getBoundingClientRect()
          , o = t.clientX - s.left - s.width / 2
          , n = t.clientY - s.top - s.height / 2;
        e.style.transform = `translate(${o * .2}px, ${n * .2}px)`
    }
    ),
    e.addEventListener("mouseleave", () => {
        e.style.transform = "translate(0, 0)",
        e.style.transition = "transform 0.3s ease",
        setTimeout( () => {
            e.style.transition = ""
        }
        , 300)
    }
    )
}
);
const w = document.querySelectorAll(".project-card");
w.forEach(e => {
    e.addEventListener("mousemove", t => {
        const s = e.getBoundingClientRect()
          , o = t.clientX - s.left
          , n = t.clientY - s.top
          , a = s.width / 2
          , v = s.height / 2
          , f = (n - v) / 20
          , y = (a - o) / 20;
        e.style.transform = `perspective(1000px) rotateX(${f}deg) rotateY(${y}deg) translateY(-5px)`
    }
    ),
    e.addEventListener("mouseleave", () => {
        e.style.transform = "perspective(1000px) rotateX(0) rotateY(0) translateY(0)"
    }
    )
}
);
